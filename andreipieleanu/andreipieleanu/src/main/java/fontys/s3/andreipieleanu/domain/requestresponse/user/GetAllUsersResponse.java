package fontys.s3.andreipieleanu.domain.requestresponse.user;

import fontys.s3.andreipieleanu.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUsersResponse {
    private List<User> allUsers;
}
