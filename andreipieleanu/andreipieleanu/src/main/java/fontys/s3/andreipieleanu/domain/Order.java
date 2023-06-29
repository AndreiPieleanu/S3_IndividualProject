package fontys.s3.andreipieleanu.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {
    @EqualsAndHashCode.Exclude
    private Integer id;
    private OrderStatus status;
    @EqualsAndHashCode.Exclude
    private User customer;
    private Address address;
    private LocalDateTime createdDate;
    private Map<Integer, OrderItem> orderItems;
}
