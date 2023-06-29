package fontys.s3.andreipieleanu.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clothes {
    @EqualsAndHashCode.Exclude
    private Integer id;
    private String name;
    private String description;
    private Double price;
    @EqualsAndHashCode.Exclude
    private Integer amountInStock;
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Boolean isActive = true;
    private Sizes size;
    private Integer measAmount;
    private String measUnit;
    private Category subcategory;
}
