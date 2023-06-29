package fontys.s3.andreipieleanu.servicelayer.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicatedClothesException extends ResponseStatusException {
    public DuplicatedClothesException() {
        super(HttpStatus.BAD_REQUEST, "CLOTHES IS ALREADY ADDED");
    }
}
