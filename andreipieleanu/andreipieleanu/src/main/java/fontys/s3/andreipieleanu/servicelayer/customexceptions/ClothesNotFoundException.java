package fontys.s3.andreipieleanu.servicelayer.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ClothesNotFoundException extends ResponseStatusException {
    public ClothesNotFoundException() {
        super(HttpStatus.NOT_FOUND, "CLOTHES COULD NOT BE FOUND");
    }
}
