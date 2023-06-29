package fontys.s3.andreipieleanu.datalayer.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cs_category")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Column(name = "category_name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_parent_id", referencedColumnName = "category_id")
    @EqualsAndHashCode.Exclude
    private CategoryEntity parentCategory;
}
