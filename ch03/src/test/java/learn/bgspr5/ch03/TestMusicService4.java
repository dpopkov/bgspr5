package learn.bgspr5.ch03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = "/config-04.xml")
public class TestMusicService4 extends AbstractTestNGSpringContextTests {

    @Autowired
    MusicService service;
    @Autowired
    MusicServiceTests tests;

    @Test
    public void testSongVoting() {
        tests.testSongVoting(service);
    }

    @Test
    public void testSongsForArtist() {
        tests.testSongsForArtist(service);
    }

    @Test
    public void testMatchingArtistNames() {
        tests.testMatchingArtistNames(service);
    }

    @Test
    public void testMatchingSongNamesForArtist() {
        tests.testMatchingSongNamesForArtist(service);
    }
}
