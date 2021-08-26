package learn.bgspr5.ch07;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ArtistController {

    private final ArtistRepository repository;

    public ArtistController(ArtistRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/artist/{id}")
    Artist findArtistById(@PathVariable int id) throws SQLException {
        return repository.findArtistById(id);
    }

    @GetMapping({"/artist/search/{name}", "/artist/search/"})
    Artist findArtistByName(@PathVariable(required = false) String name) throws SQLException {
        if (name != null) {
            return repository.findArtistByName(name);
        } else {
            throw new IllegalArgumentException("No artist name submitted");
        }
    }

    @PostMapping("/artist")
    Artist saveArtist(@RequestBody Artist artist) throws SQLException {
        return repository.saveArtist(artist.getName());
    }

    @GetMapping({"/artist/match/{name}", "/artist/match/"})
    List<Artist> findArtistByMatchingName(@PathVariable(required = false) String name) throws SQLException {
        return repository.findAllArtistsByName(name != null ? name : "");
    }
}
