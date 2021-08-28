package learn.bgspr5.ch08;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class MusicRepository {

    private final JdbcTemplate jdbcTemplate;

    private final SongRowMapper songRowMapper;

    public MusicRepository(JdbcTemplate jdbcTemplate, SongRowMapper songRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.songRowMapper = songRowMapper;
    }

    @Transactional
    public Optional<Artist> findArtistById(Integer id) {
        String select = "SELECT id, name FROM artists WHERE id = ?";
        return jdbcTemplate.query(select,
                    new Object[]{id},
                    new int[]{Types.BIGINT},
                    (rs, rowNum) -> new Artist(rs.getInt("id"), rs.getString("name")))
                .stream()
                .findFirst();
    }

    @Transactional
    public Optional<Artist> findArtistByName(String name) {
        return internalFindArtistByName(name, true);
    }

    @Transactional
    public Optional<Artist> findArtistByNameNoUpdate(String name) {
        return internalFindArtistByName(name, false);
    }

    private Optional<Artist> internalFindArtistByName(String name, boolean update) {
        final String selectSql = "SELECT id, name FROM artists WHERE LOWER(NAME) = LOWER(?)";
        final Optional<Artist> found = jdbcTemplate.query(
                selectSql,
                new Object[]{name},
                new int[]{Types.VARCHAR},
                (rs, rowNum) -> new Artist(rs.getInt("id"), rs.getString("name"))
        )
                .stream()
                .findFirst();
        return found.isPresent() ? found : getInsertedArtist(name, update);
    }

    private Optional<Artist> getInsertedArtist(String name, boolean update) {
        if (update) {
            final String insertSql = "INSERT INTO artists (name) VALUES (?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                return ps;
            }, keyHolder);
            return Optional.of(new Artist(getGeneratedId(keyHolder), name));
        } else {
            return Optional.empty();
        }
    }

    private int getGeneratedId(KeyHolder keyHolder) {
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("Failed to retrieve generated ID");
        }
        return key.intValue();
    }

    @Transactional
    public List<Song> getSongsForArtist(String artistName) {
        final String selectSql = "SELECT id, artist_id, name, votes FROM songs WHERE artist_id = ? "
                + "ORDER BY votes DESC, name ASC";
        Artist artist = internalFindOrInsertArtist(artistName);
        return jdbcTemplate.query(
                selectSql,
                new Object[]{artist.getId()},
                new int[]{Types.INTEGER},
                songRowMapper
        );
    }

    private Artist internalFindOrInsertArtist(String artistName) {
        return internalFindArtistByName(artistName, true)
                .orElseThrow(() -> new IllegalStateException("Failed to find artist with name " + artistName));
    }

    @Transactional
    public List<String> getMatchingSongNamesForArtist(String artistName, String prefix) {
        final String selectSql = "SELECT name FROM songs WHERE artist_id = ? "
                + "AND LOWER(name) LIKE LOWER(?) ORDER BY name ASC";
        Artist artist = internalFindOrInsertArtist(artistName);
        return jdbcTemplate.query(
                selectSql,
                new Object[]{artist.getId(), prefix + "%"},
                new int[]{Types.INTEGER, Types.VARCHAR},
                (rs, rowNum) -> rs.getString("name")
        );
    }

    @Transactional
    public List<String> getMatchingArtistNames(String prefix) {
        final String selectSql = "SELECT name FROM artists WHERE LOWER(name) LIKE LOWER(?) ORDER BY NAME ASC";
        return jdbcTemplate.query(
                selectSql,
                new Object[]{prefix + "%"},
                new int[]{Types.VARCHAR},
                (rs, rowNum) -> rs.getString("name")
        );
    }

    @Transactional
    public Song getSong(String artistName, String name) {
        return internalGetSong(artistName, name);
    }

    private Song internalGetSong(String artistName, String name) {
        final String selectSql = "SELECT id, artist_id, name, votes FROM songs "
                + "WHERE artist_id = ? AND LOWER(name) = LOWER(?)";
        Artist artist = internalFindOrInsertArtist(artistName);
        return jdbcTemplate.query(
                selectSql,
                new Object[]{artist.getId(), name},
                new int[]{Types.INTEGER, Types.VARCHAR},
                songRowMapper
        )
                .stream()
                .findFirst()
                .orElseGet(() -> internalInsertSong(artist.getId(), name));
    }

    private Song internalInsertSong(int artistId, String name) {
        final String insertSql = "INSERT INTO songs (artist_id, name, votes) VALUES (?, ?, 0)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, artistId);
            ps.setString(2, name);
            return ps;
        }, keyHolder);
        return new Song(getGeneratedId(keyHolder), artistId, name, 0);
    }

    @Transactional
    public Song voteForSong(String artistName, String name) {
        final String updateSql = "UPDATE songs SET votes = ? WHERE id = ?";
        Song song = internalGetSong(artistName, name);
        song.setVotes(song.getVotes() + 1);
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(updateSql);
            ps.setInt(1, song.getVotes());
            ps.setInt(2, song.getId());
            return ps;
        });
        return song;
    }
}
