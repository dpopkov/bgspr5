package learn.bgspr5.ch03;

import learn.bgspr5.ch03.mem02.SimpleNormalizer;
import learn.bgspr5.ch03.mem03.CapLeadingNormalizer;
import learn.bgspr5.ch03.mem04.MusicService4;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuration10 {
    @Bean
    Normalizer simpleNormalizer() {
        return new SimpleNormalizer();
    }

    @Bean
    Normalizer capNormalizer() {
        return new CapLeadingNormalizer();
    }

    @Bean
    MusicService musicService(Normalizer simpleNormalizer,
                              @Qualifier("capNormalizer") Normalizer bar) {
        return new MusicService4(bar, simpleNormalizer);
    }
}
