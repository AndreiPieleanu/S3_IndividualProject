package fontys.s3.andreipieleanu.domain.requestresponse.address;

import fontys.s3.andreipieleanu.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAddressResponse {
    Map<Integer, Address> foundAddresses;
}
