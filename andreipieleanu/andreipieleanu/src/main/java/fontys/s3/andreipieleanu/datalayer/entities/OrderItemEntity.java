package fontys.s3.andreipieleanu.datalayer.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cs_order_items")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "clothes_id", referencedColumnName = "clothes_id")
    @NotNull
    private ClothesEntity item;
    @Column(name = "item_amount")
    @NotNull
    private Integer amount;
    @Column(name = "item_price")
    @NotNull
    private Double price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrderEntity orderEntity;
}
