package learn.bgspr5.ch09.jpa;

import learn.bgspr5.ch09.test.BaseSongRepositoryTests;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class SongRepositoryTest extends BaseSongRepositoryTests<Artist, Song, Integer> {

    @Override
    protected Artist createArtist(String name) {
        return new Artist(name);
    }

    @Override
    protected Song createSong(Artist artist, String name) {
        return new Song(name, artist);
    }
}
