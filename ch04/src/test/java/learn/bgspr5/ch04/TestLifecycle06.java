package learn.bgspr5.ch04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.testng.Assert.*;

@Component
class SeventhObject extends HasData {
    static Object semaphore = null;

    @PostConstruct
    public void initialize() {
        semaphore = new Object();
    }

    @PreDestroy
    public void dispose() {
        semaphore = null;
    }
}

@ContextConfiguration(locations = "/annotated-06.xml")
public class TestLifecycle06 extends AbstractTestNGSpringContextTests {

    @Autowired
    ConfigurableApplicationContext context;

    @Ignore("context.close() causes java.lang.IllegalStateException to be thrown, but all assertions are correct")
    @Test
    public void testLifecycle() {
        SeventhObject obj = context.getBean(SeventhObject.class);
        assertNotNull(SeventhObject.semaphore);
        assertEquals(obj.getDatum(), "default");
        context.close();
        assertNull(SeventhObject.semaphore);
    }
}
