package fontys.s3.andreipieleanu.domain.requestresponse.order;

import fontys.s3.andreipieleanu.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderStatusesResponse {
    public List<OrderStatus> statuses;
}
