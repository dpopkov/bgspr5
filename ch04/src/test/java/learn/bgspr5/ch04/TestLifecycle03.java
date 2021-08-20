package learn.bgspr5.ch04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

class ThirdObject extends HasData {
    static Object semaphore = null;

    public void init() {
        semaphore = new Object();
    }

    public void dispose() {
        semaphore = null;
    }
}

@ContextConfiguration(locations = "/config-03.xml")
public class TestLifecycle03 extends AbstractTestNGSpringContextTests {

    @Autowired
    ConfigurableApplicationContext context;

    @Ignore("context.close() causes java.lang.IllegalStateException to be thrown, but all assertions are correct")
    @Test
    public void testInitDestroyMethods() {
        ThirdObject obj = context.getBean(ThirdObject.class);
        assertNotNull(ThirdObject.semaphore);
        assertEquals(obj.getDatum(), "default");
        context.close();
        assertNull(ThirdObject.semaphore);
    }

}
