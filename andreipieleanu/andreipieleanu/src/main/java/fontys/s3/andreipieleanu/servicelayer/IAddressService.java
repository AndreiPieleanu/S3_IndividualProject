package fontys.s3.andreipieleanu.servicelayer;

import fontys.s3.andreipieleanu.domain.requestresponse.address.*;


public interface IAddressService {
    GetAddressResponse getAllAddressesOfUserWithId(GetAddressRequest request);
    CreateAddressResponse createAddress(CreateAddressRequest request);
    UpdateAddressResponse updateAddress(UpdateAddressRequest request);
    void deleteAddressWithId(RemoveAddressRequest request);
}
