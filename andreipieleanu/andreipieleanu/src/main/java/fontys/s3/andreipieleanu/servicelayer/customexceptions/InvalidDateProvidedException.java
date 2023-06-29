package fontys.s3.andreipieleanu.servicelayer.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidDateProvidedException  extends ResponseStatusException {
    public InvalidDateProvidedException() {
        super(HttpStatus.BAD_REQUEST, "DATE MUST HAVE FORMAT: dd.MM.yyyy");
    }
}