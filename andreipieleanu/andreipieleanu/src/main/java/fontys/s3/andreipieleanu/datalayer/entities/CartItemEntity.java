package fontys.s3.andreipieleanu.datalayer.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cs_cart_item")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    @ToString.Exclude
    private Integer id;
    @Column(name = "item_amount")
    @NotNull
    private Integer amount;
    @ManyToOne
    @JoinColumn(name = "clothes_id", referencedColumnName = "clothes_id")
    @NotNull
    private ClothesEntity item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id")
    @JsonIgnoreProperties("cartItems")
    @ToString.Exclude
    private ShoppingCartEntity shoppingCartEntity;
}
