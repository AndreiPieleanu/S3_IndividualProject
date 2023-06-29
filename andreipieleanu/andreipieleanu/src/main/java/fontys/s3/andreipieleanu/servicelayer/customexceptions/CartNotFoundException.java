package fontys.s3.andreipieleanu.servicelayer.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CartNotFoundException extends ResponseStatusException {
    public CartNotFoundException() {
        super(HttpStatus.NOT_FOUND, "CART COULD NOT BE FOUND");
    }
}