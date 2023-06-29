package fontys.s3.andreipieleanu.servicelayer.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicatedUserException extends ResponseStatusException {
    public DuplicatedUserException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "USER WITH SUCH CREDENTIALS ALREADY EXISTS!");
    }
}
