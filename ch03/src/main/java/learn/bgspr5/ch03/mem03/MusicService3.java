package learn.bgspr5.ch03.mem03;

import learn.bgspr5.ch03.AbstractMusicService;
import learn.bgspr5.ch03.Normalizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope()
public class MusicService3 extends AbstractMusicService {
    @Autowired
    @Qualifier("barNormalizer")
    Normalizer artistNormalizer;
    @Autowired
    @Qualifier("fooNormalizer")
    Normalizer songNormalizer;

    @Override
    protected String transformArtist(String input) {
        return artistNormalizer.transform(input);
    }

    @Override
    protected String transformSong(String input) {
        return songNormalizer.transform(input);
    }

    public Normalizer getArtistNormalizer() {
        return artistNormalizer;
    }

    public void setArtistNormalizer(Normalizer artistNormalizer) {
        this.artistNormalizer = artistNormalizer;
    }

    public Normalizer getSongNormalizer() {
        return songNormalizer;
    }

    public void setSongNormalizer(Normalizer songNormalizer) {
        this.songNormalizer = songNormalizer;
    }
}
