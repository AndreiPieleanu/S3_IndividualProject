package fontys.s3.andreipieleanu.domain.requestresponse.order;

import fontys.s3.andreipieleanu.datalayer.entities.ClothesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTop5MostSoldProductsResponse {
    private Map<ClothesEntity, Long> soldProducts;
}
