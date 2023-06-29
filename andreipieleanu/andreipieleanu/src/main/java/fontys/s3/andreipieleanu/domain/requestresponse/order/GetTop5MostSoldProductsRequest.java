package fontys.s3.andreipieleanu.domain.requestresponse.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTop5MostSoldProductsRequest {
    private String startDate;
    private String endDate;
}
