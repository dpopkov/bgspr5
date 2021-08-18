package learn.bgspr5.ch03;

import learn.bgspr5.ch03.model.Song;

import java.util.List;
import java.util.function.Consumer;

import static org.testng.Assert.assertEquals;

public class MusicServiceTests {

    private final Object[][] model = new Object[][]{
            {"Threadbare Bear", "Someone Stole the Flour", 4},
            {"Threadbare Bear", "What Happened To Our First CD?", 17},
            {"Therapy Rocket", "Medium", 4},
            {"Clancy in Salt", "Igneous", 5}
    };

    void iterateOverModel(Consumer<Object[]> consumer) {
        for (Object[] dataRow : model) {
            consumer.accept(dataRow);
        }
    }

    void populateService(MusicService service) {
        iterateOverModel(dataRow -> {
            for (int i = 0; i < (Integer) dataRow[2]; i++) {
                service.voteForSong((String) dataRow[0], (String) dataRow[1]);
            }
        });
    }

    void reset(MusicService service) {
        if (service instanceof Resettable) {
            ((Resettable) service).reset();
        } else {
            throw new RuntimeException(service + " does not implement Resettable");
        }
    }

    void testSongVoting(MusicService service) {
        reset(service);
        populateService(service);
        iterateOverModel(dataRow ->
                assertEquals(
                        service.getSong((String) dataRow[0], (String) dataRow[1]).getVotes(),
                        ((Integer) dataRow[2]).intValue()
                ));
    }

    void testSongsForArtist(MusicService service) {
        reset(service);
        populateService(service);
        List<Song> songs = service.getSongsForArtist("Threadbare Bear");
        assertEquals(songs.size(), 2);
        assertEquals(songs.get(0).getName(), "What Happened To Our First CD?");
        assertEquals(songs.get(0).getVotes(), 17);
        assertEquals(songs.get(1).getName(), "Someone Stole the Flour");
        assertEquals(songs.get(1).getVotes(), 4);
    }

    void testMatchingArtistNames(MusicService service) {
        reset(service);
        populateService(service);
        List<String> names = service.getMatchingArtistNames("Th");
        assertEquals(names.size(), 2);
        assertEquals(names.get(0), "Therapy Rocket");
        assertEquals(names.get(1), "Threadbare Bear");
    }

    void testMatchingSongNamesForArtist(MusicService service) {
        reset(service);
        populateService(service);
        List<String> names = service.getMatchingSongNamesForArtist(
                "Threadbare Bear", "W"
        );
        assertEquals(names.size(), 1);
        assertEquals(names.get(0), "What Happened To Our First CD?");
    }
}
