package fontys.s3.andreipieleanu.domain.requestresponse.order;

import fontys.s3.andreipieleanu.domain.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {
    private OrderStatusEnum newStatus;
    private Integer orderId;
}
