package learn.bgspr5.ch03.mem04;

import learn.bgspr5.ch03.AbstractMusicService;
import learn.bgspr5.ch03.Normalizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class MusicService4 extends AbstractMusicService {

    private final Normalizer artistNormalizer;
    private final Normalizer songNormalizer;

    public MusicService4(
            @Autowired
            @Qualifier("barNormalizer")
                    Normalizer artistNormalizer,
            @Autowired
            @Qualifier("fooNormalizer")
                    Normalizer songNormalizer
    ) {
        this.artistNormalizer = artistNormalizer;
        this.songNormalizer = songNormalizer;
    }

    @Override
    protected String transformArtist(String input) {
        return artistNormalizer.transform(input);
    }

    @Override
    protected String transformSong(String input) {
        return songNormalizer.transform(input);
    }
}
