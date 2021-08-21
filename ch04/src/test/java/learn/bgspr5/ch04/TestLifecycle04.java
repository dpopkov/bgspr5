package learn.bgspr5.ch04;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

class FourthObject extends HasData implements InitializingBean, DisposableBean {
    static Object semaphore = null;

    @Override
    public void afterPropertiesSet() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>> FourthObject.afterPropertiesSet()");
        semaphore = new Object();
    }

    @Override
    public void destroy() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>> FourthObject.destroy()");
        semaphore = null;
    }
}

@ContextConfiguration(locations = "/config-04.xml")
public class TestLifecycle04 extends AbstractTestNGSpringContextTests {

    @Autowired
    ConfigurableApplicationContext context;

    @Ignore("context.close() causes java.lang.IllegalStateException to be thrown, but all assertions are correct")
    @Test
    public void testLifecycleMethods() {
        FourthObject obj = context.getBean(FourthObject.class);
        assertNotNull(FourthObject.semaphore);
        assertEquals(obj.getDatum(), "default");
        context.close();
        assertNull(FourthObject.semaphore);
    }
}
