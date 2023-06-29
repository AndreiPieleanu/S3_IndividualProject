package fontys.s3.andreipieleanu.domain.requestresponse.clothes;

import fontys.s3.andreipieleanu.domain.Clothes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindOneClothResponse {
    private Clothes foundClothes;
}
