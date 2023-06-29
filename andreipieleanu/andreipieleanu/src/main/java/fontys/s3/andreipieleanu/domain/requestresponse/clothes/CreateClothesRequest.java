package fontys.s3.andreipieleanu.domain.requestresponse.clothes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClothesRequest {
    private String name;
    private String description;
    private double price;
    private int amountInStock;
    private boolean isActive = true;
    private int size;
    private int measAmount;
    private String measUnit;
    private int subcategoryId;
}
