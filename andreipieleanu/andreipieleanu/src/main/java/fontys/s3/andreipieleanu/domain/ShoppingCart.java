package fontys.s3.andreipieleanu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShoppingCart {
    private Integer id;
    private User user;
    private Map<Integer, ShoppingCartItem>cartItems;
}
