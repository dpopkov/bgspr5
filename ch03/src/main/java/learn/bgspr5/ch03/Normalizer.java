package learn.bgspr5.ch03;

public interface Normalizer {

    default String transform(String input) {
        return input.trim();
    }
}
