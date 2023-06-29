package fontys.s3.andreipieleanu.servicelayer.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicatedAddressException extends ResponseStatusException {
    public DuplicatedAddressException() {
        super(HttpStatus.BAD_REQUEST, "ADDRESS IS ALREADY ADDED");
    }
}
