package fontys.s3.andreipieleanu.domain.requestresponse.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveItemFromCartRequest {
    private Integer clothesId;
    private Integer cartId;
}
