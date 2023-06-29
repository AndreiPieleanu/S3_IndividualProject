package fontys.s3.andreipieleanu.domain.requestresponse.clothes;

import fontys.s3.andreipieleanu.domain.Clothes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetClothesResponse {
    private Map<Integer, Clothes> foundClothes;
}
