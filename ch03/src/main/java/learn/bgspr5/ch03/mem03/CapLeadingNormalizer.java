package learn.bgspr5.ch03.mem03;

import learn.bgspr5.ch03.Normalizer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("barNormalizer")
public class CapLeadingNormalizer implements Normalizer {

    @Override
    public String transform(String input) {
        return Stream.of(input.trim().split("\\s"))
                .filter(t -> !t.isBlank())
                .map(t -> Character.toUpperCase(t.charAt(0)) + t.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
