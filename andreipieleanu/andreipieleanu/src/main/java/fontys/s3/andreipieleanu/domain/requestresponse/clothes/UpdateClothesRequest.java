package fontys.s3.andreipieleanu.domain.requestresponse.clothes;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClothesRequest {
    private int id;
    private String name;
    private String description;
    private double price;
    private int amountInStock;
    private boolean isActive;
    private int size;
    private int measAmount;
    private String measUnit;
    private int subcategoryId;
}
