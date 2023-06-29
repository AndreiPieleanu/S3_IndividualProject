package fontys.s3.andreipieleanu.domain.requestresponse.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressRequest {
    private String streetName;
    private int streetNumber;
    private String zipCode;
    private String city;
    private String country;
    private String phone;
    private int clientId;
}
