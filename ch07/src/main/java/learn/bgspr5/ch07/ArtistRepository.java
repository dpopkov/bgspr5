package learn.bgspr5.ch07;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArtistRepository {

    private final DataSource dataSource;

    public ArtistRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Artist findArtistById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return findArtistById(conn, id);
        }
    }

    private Artist findArtistById(Connection conn, int id) {
        String sql = "SELECT name FROM artists WHERE id = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            final ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                return new Artist(id, name);
            } else {
                throw new ArtistNotFoundException("Artist with id " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new ArtistNotFoundException(e);
        }
    }

    public Artist saveArtist(String name) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try {
                return saveArtist(conn, name);
            } catch (SQLException e) {
                return findArtistByName(conn, name);
            }
        }
    }

    private Artist saveArtist(Connection conn, String name) throws SQLException {
        String sql = "INSERT INTO artists (name) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                int id = rs.getInt(1);
                return new Artist(id, name);
            }
        }
    }

    public Artist findArtistByName(String name) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return findArtistByName(conn, name);
        }
    }

    private Artist findArtistByName(Connection conn, String name) {
        String sql = "SELECT id, name FROM artists WHERE LOWER(name) = LOWER(?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, name);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nameFound = rs.getString("name");
                    return new Artist(id, nameFound);
                } else {
                    throw new ArtistNotFoundException("Artist with name " + name + " not found");
                }
            }
        } catch (SQLException e) {
            throw new ArtistNotFoundException(e);
        }
    }

    public List<Artist> findAllArtistsByName(String name) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            return findAllArtistsByName(conn, name);
        }
    }

    private List<Artist> findAllArtistsByName(Connection conn, String name) {
        String sql = "SELECT id, name FROM artists WHERE LOWER(name) LIKE LOWER(?) ORDER BY name";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name + "%");
            List<Artist> artists = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nameFound = rs.getString("name");
                    artists.add(new Artist(id, nameFound));
                }
            }
            return artists;
        } catch (SQLException e) {
            throw new ArtistNotFoundException(e);
        }
    }
}
