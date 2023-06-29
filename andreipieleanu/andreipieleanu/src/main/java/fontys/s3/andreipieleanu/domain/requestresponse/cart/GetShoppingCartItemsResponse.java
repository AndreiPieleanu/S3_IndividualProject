package fontys.s3.andreipieleanu.domain.requestresponse.cart;

import fontys.s3.andreipieleanu.domain.ShoppingCartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetShoppingCartItemsResponse {
    private Map<Integer, ShoppingCartItem> cartItems;
}
