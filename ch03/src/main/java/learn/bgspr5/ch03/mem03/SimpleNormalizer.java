package learn.bgspr5.ch03.mem03;

import learn.bgspr5.ch03.Normalizer;
import org.springframework.stereotype.Component;

@Component("fooNormalizer")
public class SimpleNormalizer implements Normalizer {
    // inherits the default transform() method
}
