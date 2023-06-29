package fontys.s3.andreipieleanu.servicelayer.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CartItemNotFoundException extends ResponseStatusException {
    public CartItemNotFoundException() {
        super(HttpStatus.NOT_FOUND, "CART ITEM COULD NOT BE FOUND");
    }
}