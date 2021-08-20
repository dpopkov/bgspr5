package learn.bgspr5.ch04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.*;

class FirstObject extends HasData {
}

@ContextConfiguration(locations = "/config-01.xml")
public class TestLifecycle01 extends AbstractTestNGSpringContextTests {

    @Autowired
    ApplicationContext context;

    @DataProvider
    Object[][] getReferences() {
        return new Object[][] {
                {"foo", true},
                {"bar", false}
        };
    }

    @Test(dataProvider = "getReferences")
    public void testReferenceTypes(String name, boolean singleton) {
        HasData obj1 = context.getBean(name, HasData.class);
        String defaultValue = obj1.getDatum();
        obj1.setDatum(UUID.randomUUID().toString());

        HasData obj2 = context.getBean(name, HasData.class);
        if (singleton) {
            assertSame(obj1, obj2);
            assertEquals(obj1, obj2);
            assertNotEquals(defaultValue, obj2.getDatum());
        } else {
            assertNotSame(obj1, obj2);
            assertNotEquals(obj1, obj2);
            assertEquals(defaultValue, obj2.getDatum());
        }
    }
}
