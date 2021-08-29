package learn.bgspr5.ch08;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Artist not found")
public class ArtistNotFoundException extends RuntimeException {

    public ArtistNotFoundException(String message) {
        super(message);
    }
}
