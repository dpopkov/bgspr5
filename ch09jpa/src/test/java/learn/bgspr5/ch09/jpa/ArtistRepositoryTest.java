package learn.bgspr5.ch09.jpa;

import learn.bgspr5.ch09.test.BaseArtistRepositoryTests;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ArtistRepositoryTest extends BaseArtistRepositoryTests<Artist, Integer> {

    @Override
    protected Artist createArtist(String name) {
        return new Artist(name);
    }
}
