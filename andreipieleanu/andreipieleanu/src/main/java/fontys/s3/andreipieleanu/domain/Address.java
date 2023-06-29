package fontys.s3.andreipieleanu.domain;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Address {
    @EqualsAndHashCode.Exclude
    @Setter
    private Integer id;
    private String streetName;
    private Integer streetNumber;
    private String zipCode;
    private String city;
    private String country;
    private String phone;
    private User user;
}
