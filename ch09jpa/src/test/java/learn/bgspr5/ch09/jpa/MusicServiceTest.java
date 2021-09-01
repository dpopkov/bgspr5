package learn.bgspr5.ch09.jpa;

import learn.bgspr5.ch09.test.BaseMusicServicesTests;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MusicServiceTest extends BaseMusicServicesTests<Artist, Song, Integer> {

    @Override
    protected Integer getNonexistentId() {
        return Integer.MAX_VALUE;
    }
}
