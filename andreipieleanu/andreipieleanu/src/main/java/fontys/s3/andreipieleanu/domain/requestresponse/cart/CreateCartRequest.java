package fontys.s3.andreipieleanu.domain.requestresponse.cart;

import fontys.s3.andreipieleanu.datalayer.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartRequest {
    private UserEntity user;
}
