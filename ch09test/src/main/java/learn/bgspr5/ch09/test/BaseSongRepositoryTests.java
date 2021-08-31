package learn.bgspr5.ch09.test;

import learn.bgspr5.ch09.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.assertEquals;

public abstract class BaseSongRepositoryTests<
            A extends BaseArtist<ID>,
            S extends BaseSong<A, ID>,
            ID
        > extends AbstractTestNGSpringContextTests {
    @Autowired
    BaseArtistRepository<A, ID> artistRepository;
    @Autowired
    BaseSongRepository<A, S, ID> songRepository;
    @Autowired
    WildCardConverter converter;

    protected abstract A createArtist(String name);

    protected abstract S createSong(A artist, String name);

    @BeforeMethod
    public void clearDatabase() {
        artistRepository.deleteAll();
        songRepository.deleteAll();
        buildModel();
    }

    private final Object[][] model = new Object[][]{
            {"Threadbare Loaf", "Someone Stole the Flour", 4},
            {"Threadbare Loaf", "What Happened To Our First CD?", 17},
            {"Therapy Zeppelin", "Mambledumble Is Not A Word", 0},
            {"Therapy Zeppelin", "Medium", 4},
            {"Clancy in Silt", "Igneous", 5}
    };

    private void buildModel() {
        for (Object[] dataRow : model) {
            String artistName = (String) dataRow[0];
            String songTitle = (String) dataRow[1];
            Integer votes = (Integer) dataRow[2];
            Optional<A> artistQuery = artistRepository.findByNameIgnoreCase(artistName);
            A artist = artistQuery.orElseGet(() -> {
                A entity = createArtist(artistName);
                artistRepository.save(entity);
                return entity;
            });
            Optional<S> songQuery = songRepository
                    .findByArtistIdAndNameIgnoreCase(artist.getId(), songTitle);
            if (songQuery.isEmpty()) {
                S song = createSong(artist, songTitle);
                song.setVotes(votes);
                songRepository.save(song);
            }
        }
    }

    @Test
    public void testOperations() {
        A artist = artistRepository.findByNameIgnoreCase("therapy zeppelin").orElseThrow();
        List<S> songs = songRepository.findByArtistIdAndNameLikeIgnoreCaseOrderByNameDesc(
                artist.getId(), converter.convertToWildCard("m"));
        assertEquals(songs.size(), 2);

        songs = songRepository.findByArtistIdOrderByVotesDesc(artist.getId());
        assertEquals(songs.size(), 2);

        assertEquals(songs.get(0).getName(), "Medium");
        assertEquals(songs.get(0).getVotes(), 4);
        assertEquals(songs.get(1).getVotes(), 0);
    }
}
