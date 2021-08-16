package learn.bgspr5.ch02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.testng.Assert.*;

public class GreeterTest {

    @Test
    public void testHelloWorld() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
        Greeter greeter = context.getBean("helloGreeter", Greeter.class);
        final ByteArrayOutputStream baos = context.getBean("baos", ByteArrayOutputStream.class);

        greeter.greet();

        String actual = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        assertEquals(actual, "Hello, World!");
    }
}
