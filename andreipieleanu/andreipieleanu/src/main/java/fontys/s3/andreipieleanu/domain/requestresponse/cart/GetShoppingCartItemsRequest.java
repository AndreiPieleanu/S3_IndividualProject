package fontys.s3.andreipieleanu.domain.requestresponse.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetShoppingCartItemsRequest {
    private Integer cartId;
}
