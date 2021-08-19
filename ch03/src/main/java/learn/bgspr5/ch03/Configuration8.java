package learn.bgspr5.ch03;

import learn.bgspr5.ch03.mem02.MusicService2;
import learn.bgspr5.ch03.mem02.SimpleNormalizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuration8 {
    @Bean
    Normalizer normalizer() {
        return new SimpleNormalizer();
    }

    @Bean
    MusicService musicService() {
        return new MusicService2();
    }
}
