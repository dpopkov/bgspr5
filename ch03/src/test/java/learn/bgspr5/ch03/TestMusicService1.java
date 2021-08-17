package learn.bgspr5.ch03;

import learn.bgspr5.ch03.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.*;

@ContextConfiguration(locations = "/config-01.xml")
public class TestMusicService1 extends AbstractTestNGSpringContextTests {
    @Autowired
    ApplicationContext context;
    @Autowired
    MusicService service;

    @Test
    public void testConfiguration() {
        assertNotNull(context);
        Set<String> definitions = new HashSet<>(Arrays.asList(context.getBeanDefinitionNames()));
        assertTrue(definitions.contains("musicService1"));
    }

    @Test
    public void testMusicService() {
        Song song = service.getSong("Threadbare Bear", "Someone Stole the Wine");
        assertEquals(song.getVotes(), 0);
    }
}
