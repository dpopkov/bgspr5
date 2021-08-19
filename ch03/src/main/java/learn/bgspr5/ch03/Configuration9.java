package learn.bgspr5.ch03;

import learn.bgspr5.ch03.mem02.SimpleNormalizer;
import learn.bgspr5.ch03.mem03.CapLeadingNormalizer;
import learn.bgspr5.ch03.mem03.MusicService3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuration9 {
    @Bean(name = "fooNormalizer")
    Normalizer simpleNormalizer() {
        return new SimpleNormalizer();
    }

    @Bean(name = "barNormalizer")
    Normalizer capNormalizer() {
        return new CapLeadingNormalizer();
    }

    @Bean
    MusicService musicService() {
        return new MusicService3();
    }
}
