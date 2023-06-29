package fontys.s3.andreipieleanu.datalayer.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "cs_shopping_cart")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_id")
    private Integer id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @NotNull
    private UserEntity user;
    @OneToMany(mappedBy = "shoppingCartEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @MapKeyColumn(name = "cart_item_id")
    @JsonIgnoreProperties("shoppingCartEntity")
    @NotNull
    private Map<Integer, CartItemEntity> cartItems = new HashMap<>();
}
