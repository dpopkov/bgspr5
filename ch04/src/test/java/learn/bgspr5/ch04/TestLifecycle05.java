package learn.bgspr5.ch04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.*;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
class FifthObject extends HasData {
}

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class SixthObject extends HasData {
}

@ContextConfiguration(locations = "/annotated.xml")
public class TestLifecycle05 extends AbstractTestNGSpringContextTests {

    @Autowired
    ConfigurableApplicationContext context;

    @DataProvider
    Object[][] getReferences() {
        return new Object[][]{
                {FifthObject.class, true},
                {SixthObject.class, false}
        };
    }

    @Test(dataProvider = "getReferences")
    public void testReferenceTypes(Class<HasData> clazz, boolean singleton) {
        HasData obj1 = context.getBean(clazz);
        final String defaultValue = obj1.getDatum();
        obj1.setDatum(UUID.randomUUID().toString());
        HasData obj2 = context.getBean(clazz);
        if (singleton) {
            assertSame(obj1, obj2);
            assertEquals(obj1, obj2);
            assertNotEquals(obj2.getDatum(), defaultValue);
        } else {
            assertNotSame(obj1, obj2);
            assertNotEquals(obj1, obj2);
            assertEquals(obj2.getDatum(), defaultValue);
        }
    }
}
