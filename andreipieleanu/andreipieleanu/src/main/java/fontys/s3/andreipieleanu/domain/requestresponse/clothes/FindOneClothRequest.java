package fontys.s3.andreipieleanu.domain.requestresponse.clothes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindOneClothRequest {
    private int clothesId;
}
