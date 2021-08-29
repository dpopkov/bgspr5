package learn.bgspr5.ch08;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/artists/{name}")
public class SongController {

    private final MusicRepository musicRepository;

    public SongController(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    private String decode(Object data) {
        return UriUtils.decode(data.toString(), Charset.defaultCharset());
    }

    @GetMapping(value = "/vote/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Song voteForSong(@PathVariable String name, @PathVariable String title) {
        return musicRepository.voteForSong(decode(name), decode(title));
    }

    @GetMapping(value = "/song/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Song getSong(@PathVariable String name, @PathVariable String title) {
        return musicRepository.getSong(decode(name), decode(title));
    }

    @GetMapping(value = "/songs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Song> getSongsForArtist(@PathVariable String name) {
        return musicRepository.getSongsForArtist(decode(name));
    }

    @GetMapping(value = "/match/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> findSongsForArtist(@PathVariable String name, @PathVariable(required = false) String title) {
        return musicRepository.getMatchingSongNamesForArtist(decode(name),
                title != null ? decode(title) : "");
    }
}
