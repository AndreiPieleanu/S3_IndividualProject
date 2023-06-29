package fontys.s3.andreipieleanu.servicelayer.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserRoleNotFoundException extends ResponseStatusException {
    public UserRoleNotFoundException() {
        super(HttpStatus.NOT_FOUND, "USER ROLE COULD NOT BE FOUND");
    }
}