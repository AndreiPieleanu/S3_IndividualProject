package fontys.s3.andreipieleanu.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Category {
    @EqualsAndHashCode.Exclude
    private int id;
    private String name;
    private Category parentCategory;
}
