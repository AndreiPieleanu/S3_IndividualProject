package fontys.s3.andreipieleanu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    private int id;
    private int amount;
    private Double price;
    private Clothes item;
}
