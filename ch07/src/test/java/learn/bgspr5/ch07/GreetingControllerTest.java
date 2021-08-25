package learn.bgspr5.ch07;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private GreetingController greetingController;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @DataProvider
    Object[][] greetingData() {
        return new Object[][] {
                new Object[]{null, "Hello, world!"},
                new Object[]{"World", "Hello, World!"},
                new Object[]{"Andrew", "Hello, Andrew!"},
                new Object[]{"Jack Griffin", "I don't know who you are."}
        };
    }

    @Test(dataProvider = "greetingData")
    public void testRestGreeting(String name, String expectedGreeting) {
        String url = "http://localhost:" + port + "/greeting/" + (name != null ? name : "");
        ResponseEntity<Greeting> result = restTemplate.getForEntity(url, Greeting.class);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        Greeting body = result.getBody();
        assertNotNull(body);
        assertEquals(body.getMessage(), expectedGreeting);
    }

    @Test(dataProvider = "greetingData")
    public void testDirectGreeting(String name, String expectedGreeting) {
        assertEquals(greetingController.greeting(name).getMessage(), expectedGreeting);
    }
}
