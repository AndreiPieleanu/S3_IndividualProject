package fontys.s3.andreipieleanu.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartItem {
    private Integer id;
    private Integer amount;
    private Clothes item;
}
