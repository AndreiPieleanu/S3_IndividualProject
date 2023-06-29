package fontys.s3.andreipieleanu.domain.requestresponse.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewUserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
