package learn.bgspr5.ch08;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    private final MusicRepository musicRepository;

    public ArtistController(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Artist findArtistById(@PathVariable int id) {
        return musicRepository.findArtistById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Cannot find artist with id " + id));
    }

    @GetMapping(
            value = {"/search/{name}", "/search/"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Artist findArtistByName(@PathVariable(required = false) String name) {
        if (name != null) {
            return musicRepository.findArtistByNameNoUpdate(decode(name))
                    .orElseThrow(() -> new ArtistNotFoundException("Cannot find artist with name " + name));
        } else {
            throw new NoDataSubmittedException("No artist name submitted");
        }
    }

    @PostMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Artist saveArtist(@RequestBody Artist artist) {
        return musicRepository.findArtistByName(artist.getName()).orElseThrow();
    }

    @GetMapping(
            value = {"/match/{name}", "/match/"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> findArtistsByMatchingName(@PathVariable(required = false) String name) {
        return musicRepository.getMatchingArtistNames(name != null ? decode(name) : "");
    }

    private String decode(Object data) {
        return UriUtils.decode(data.toString(), Charset.defaultCharset());
    }
}
