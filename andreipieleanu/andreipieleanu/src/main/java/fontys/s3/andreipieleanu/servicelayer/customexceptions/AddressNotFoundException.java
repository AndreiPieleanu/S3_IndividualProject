package fontys.s3.andreipieleanu.servicelayer.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AddressNotFoundException extends ResponseStatusException {
    public AddressNotFoundException() {
        super(HttpStatus.NOT_FOUND, "ADDRESS COULD NOT BE FOUND");
    }
}