package learn.bgspr5.ch04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

class SecondObject extends HasData {
    public SecondObject(String initialValue) {
        setDatum(initialValue);
    }
}

@ContextConfiguration(locations = "/config-02.xml")
public class TestLifecycle02 extends AbstractTestNGSpringContextTests {

    @Autowired
    ApplicationContext context;

    @Test
    public void validateConstruction() {
        HasData obj = context.getBean(HasData.class);
        assertEquals(obj.getDatum(), "Initial Value");
    }
}
