package fontys.s3.andreipieleanu.domain.requestresponse.item;

import fontys.s3.andreipieleanu.domain.Clothes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddItemToCartRequest {
    private int amount;
    private Clothes item;
}
