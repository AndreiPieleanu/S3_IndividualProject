package fontys.s3.andreipieleanu.datalayer.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Table(name = "cs_orders")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    @EqualsAndHashCode.Exclude
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_status_id", referencedColumnName = "order_status_id")
    private OrderStatusEntity status;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @EqualsAndHashCode.Exclude
    @NotNull
    private UserEntity customer;
    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    @NotNull
    private AddressEntity addressEntity;
    @Column(name = "created_date")
    @NotNull
    private LocalDateTime createdDate = LocalDateTime.now();
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @MapKeyColumn(name = "order_item_id")
    @JsonIgnoreProperties("orderEntity")
    @NotNull
    private Map<Integer, OrderItemEntity> orderItems = new HashMap<>();
}
