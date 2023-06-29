package fontys.s3.andreipieleanu.datalayer.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cs_clothes")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClothesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clothes_id")
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Length(max = 30)
    @Column(name = "clothes_name")
    @NotNull
    private String name;
    @Length(max = 200)
    @Column(name = "clothes_description")
    @NotNull
    private String description;
    @Column(name = "clothes_price")
    @NotNull
    private Double price;
    @EqualsAndHashCode.Exclude
    @Column(name = "clothes_amount_in_stock")
    @NotNull
    private Integer amountInStock;
    @EqualsAndHashCode.Exclude
    @Column(name = "clothes_is_active")
    private boolean isActive = true;
    @Column(name = "clothes_size")
    @NotNull
    private SizesEntity sizesEntity;
    @Column(name = "clothes_meas_amount")
    @NotNull
    private Integer measAmount;
    @Column(name = "clothes_meas_unit")
    @NotNull
    private String measUnit;
    @ManyToOne
    @JoinColumn(name = "clothes_subcategory_id", referencedColumnName = "category_id")
    @NotNull
    private CategoryEntity subcategoryEntity;
}