package learn.bgspr5.ch08;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NoDataSubmittedException extends IllegalArgumentException {
    public NoDataSubmittedException(String s) {
        super(s);
    }
}
