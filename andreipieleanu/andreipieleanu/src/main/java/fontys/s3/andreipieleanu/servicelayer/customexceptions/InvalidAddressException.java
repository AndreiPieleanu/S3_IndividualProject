package fontys.s3.andreipieleanu.servicelayer.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidAddressException extends ResponseStatusException {
    public InvalidAddressException() {
        super(HttpStatus.BAD_REQUEST, "ADDRESS THAT YOU WANT TO ADD IS INVALID");
    }

    public InvalidAddressException(String errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}