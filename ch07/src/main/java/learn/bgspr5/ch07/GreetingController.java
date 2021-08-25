package learn.bgspr5.ch07;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @RequestMapping(value = {"/greeting/{name}", "/greeting"})
    Greeting greeting(@PathVariable(required = false) String name) {
        String object = name != null ? name : "world";
        if (object.equalsIgnoreCase("jack griffin")) {
            return new Greeting("I don't know who you are.");
        } else {
            return new Greeting("Hello, " + object + "!");
        }
    }
}
