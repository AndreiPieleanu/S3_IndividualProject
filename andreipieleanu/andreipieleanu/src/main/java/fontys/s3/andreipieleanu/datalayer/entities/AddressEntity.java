package fontys.s3.andreipieleanu.datalayer.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cs_address")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    @EqualsAndHashCode.Exclude
    @Setter
    private Integer id;
    @Column(name = "street_name")
    @Length(max = 50)
    @NotNull
    private String streetName;
    @Column(name = "street_number")
    @NotNull
    private int streetNumber;
    @Column(name = "zipcode")
    @NotNull
    private String zipCode;
    @Column(name = "city")
    @NotNull
    private String city;
    @Column(name = "country")
    @NotNull
    private String country;
    @Column(name = "phone")
    @NotNull
    private String phone;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @NotNull
    private UserEntity user;
}
