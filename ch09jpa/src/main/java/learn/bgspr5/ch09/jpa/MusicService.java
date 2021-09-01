package learn.bgspr5.ch09.jpa;

import learn.bgspr5.ch09.common.BaseMusicService;
import learn.bgspr5.ch09.common.WildCardConverter;

public class MusicService extends BaseMusicService<Artist, Song, Integer> {
    protected MusicService(ArtistRepository artistRepository,
                           SongRepository songRepository,
                           WildCardConverter converter) {
        super(artistRepository, songRepository, converter);
    }

    @Override
    protected Artist createArtist(String name) {
        return new Artist(name);
    }

    @Override
    protected Song createSong(Artist artist, String name) {
        return new Song(name, artist);
    }
}
