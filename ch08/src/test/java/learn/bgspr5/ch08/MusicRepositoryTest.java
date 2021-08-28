package learn.bgspr5.ch08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.function.Consumer;

import static org.testng.Assert.*;

@SpringBootTest
public class MusicRepositoryTest extends AbstractTestNGSpringContextTests {
    @Autowired
    MusicRepository musicRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final Object[][] model = new Object[][]{
            {"Threadbare Loaf", "Someone Stole the Flour", 4},
            {"Threadbare Loaf", "What Happened To Our First CD?", 17},
            {"Therapy Zeppelin", "Medium", 4},
            {"Clancy in Salt", "Igneous", 5}
    };

    private void iterateOverModel(Consumer<Object[]> consumer) {
        for (Object[] dataRow : model) {
            consumer.accept(dataRow);
        }
    }

    private void populateData() {
        iterateOverModel(dataRow -> {
            int numVotes = (Integer) dataRow[2];
            for (int i = 0; i < numVotes; i++) {
                String artistName = (String) dataRow[0];
                String songName = (String) dataRow[1];
                musicRepository.voteForSong(artistName, songName);
            }
        });
    }

    @BeforeMethod
    void resetDb() {
        jdbcTemplate.update("DELETE FROM songs");
        jdbcTemplate.update("DELETE FROM artists");
        populateData();
    }

    @Test
    public void testSongVoting() {
        iterateOverModel(dataRow -> assertEquals(
                musicRepository.getSong((String) dataRow[0], (String) dataRow[1]).getVotes(),
                ((Integer) dataRow[2]).intValue()
        ));
    }

    @Test
    void testSongsForArtist() {
        List<Song> songs = musicRepository.getSongsForArtist("Threadbare Loaf");
        assertEquals(songs.size(), 2);
        assertEquals(songs.get(0).getName(), "What Happened To Our First CD?");
        assertEquals(songs.get(0).getVotes(), 17);
        assertEquals(songs.get(1).getName(), "Someone Stole the Flour");
        assertEquals(songs.get(1).getVotes(), 4);
    }

    @Test
    void testMatchingArtistNames() {
        List<String> names = musicRepository.getMatchingArtistNames("Th");
        assertEquals(names.size(), 2);
        assertEquals(names.get(0), "Therapy Zeppelin");
        assertEquals(names.get(1), "Threadbare Loaf");
    }
    @Test
    void testMatchingSongNamesForArtist() {
        List<String> names = musicRepository.getMatchingSongNamesForArtist(
                "Threadbare Loaf", "W"
        );
        assertEquals(names.size(), 1);
        assertEquals(names.get(0), "What Happened To Our First CD?");
    }
}
