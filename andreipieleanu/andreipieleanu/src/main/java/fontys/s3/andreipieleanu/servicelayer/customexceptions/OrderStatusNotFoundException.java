package fontys.s3.andreipieleanu.servicelayer.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderStatusNotFoundException extends ResponseStatusException {
    public OrderStatusNotFoundException() {
        super(HttpStatus.NOT_FOUND, "ORDER STATUS COULD NOT BE FOUND");
    }
}