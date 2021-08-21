package learn.bgspr5.ch04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.UUID;

import static org.testng.Assert.*;

class NinthObject extends HasData {
}

@Configuration
class Config08 {

    @Bean
    public NinthObject foo() {
        return new NinthObject();
    }

    @Bean
    @Scope("prototype")
    public NinthObject bar() {
        return new NinthObject();
    }
}

@ContextConfiguration(classes = Config08.class)
public class TestLifecycle08 extends AbstractTestNGSpringContextTests {

    @Autowired
    ConfigurableApplicationContext context;

    @DataProvider
    Object[][] getReferences() {
        return new Object[][]{
                {"foo", true},
                {"bar", false}
        };
    }

    @Test(dataProvider = "getReferences")
    public void testReferenceTypes(String beanName, boolean singleton) {
        HasData obj1 = context.getBean(beanName, HasData.class);
        final String defaultValue = obj1.getDatum();
        obj1.setDatum(UUID.randomUUID().toString());
        HasData obj2 = context.getBean(beanName, HasData.class);
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
