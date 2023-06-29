package fontys.s3.andreipieleanu.datalayer.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "cs_order_status")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    private int id;
    @NotNull
    @Column(name = "order_status_value")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnumEntity statusEnum;
}
