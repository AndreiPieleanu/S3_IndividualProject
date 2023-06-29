package fontys.s3.andreipieleanu.domain.requestresponse.user;

import fontys.s3.andreipieleanu.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindUserResponse {
    private User foundUser;
}
