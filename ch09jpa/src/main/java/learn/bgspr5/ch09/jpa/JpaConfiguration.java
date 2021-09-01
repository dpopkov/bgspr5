package learn.bgspr5.ch09.jpa;

import learn.bgspr5.ch09.common.WildCardConverter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@EnableJpaRepositories
@EntityScan
public class JpaConfiguration {
    @Bean
    WildCardConverter converter() {
        return new WildCardConverter("%");
    }

    @Bean
    MusicService musicService(
            ArtistRepository artistRepository,
            SongRepository songRepository,
            WildCardConverter converter) {
        return new MusicService(artistRepository, songRepository, converter);
    }
}
