package fontys.s3.andreipieleanu.domain.requestresponse.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindUserRequest {
    private int userId;
}
