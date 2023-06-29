package fontys.s3.andreipieleanu.domain.requestresponse.cart;

import fontys.s3.andreipieleanu.domain.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetShoppingCartResponse {
    private ShoppingCart shoppingCart;
}
