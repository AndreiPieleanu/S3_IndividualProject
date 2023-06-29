package fontys.s3.andreipieleanu.domain.requestresponse.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSalesForXPastMonthsResponse {
    private Map<String, Double> retrievedSales;
}
