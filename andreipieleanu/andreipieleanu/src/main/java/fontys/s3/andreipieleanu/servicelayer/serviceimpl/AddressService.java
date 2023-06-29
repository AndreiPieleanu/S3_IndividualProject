package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.IAddressDal;
import fontys.s3.andreipieleanu.datalayer.entities.AddressEntity;
import fontys.s3.andreipieleanu.datalayer.entities.UserEntity;
import fontys.s3.andreipieleanu.domain.Address;
import fontys.s3.andreipieleanu.domain.requestresponse.address.*;
import fontys.s3.andreipieleanu.servicelayer.IAddressService;
import fontys.s3.andreipieleanu.servicelayer.converters.AddressConverter;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.AddressNotFoundException;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.DuplicatedAddressException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AddressService implements IAddressService {
    private IAddressDal addressDal;
    @Override
    public GetAddressResponse getAllAddressesOfUserWithId(GetAddressRequest request) {
        List<AddressEntity> allAddresses = addressDal.findAll();
        Map<Integer, Address> convertedAddresses = new HashMap<>();
        allAddresses.forEach((v) -> {
            if(v.getUser().getId() == request.getUserId()){
                convertedAddresses.put(v.getId(), AddressConverter.convert(v));
            }
        });
        return new GetAddressResponse(convertedAddresses);
    }

    @Override
    public CreateAddressResponse createAddress(CreateAddressRequest addressToAdd) {
        AddressEntity entityToAdd = AddressEntity
                .builder()
                .streetName(addressToAdd.getStreetName())
                .streetNumber(addressToAdd.getStreetNumber())
                .zipCode(addressToAdd.getZipCode())
                .city(addressToAdd.getCity())
                .country(addressToAdd.getCountry())
                .phone(addressToAdd.getPhone())
                .user(UserEntity.builder().id(addressToAdd.getClientId()).build())
                .build();
        if(addressDal.findAll().contains(entityToAdd)){
            throw new DuplicatedAddressException();
        }
        // validation
        AddressEntity savedAddress = addressDal.save(entityToAdd);
        return new CreateAddressResponse(savedAddress.getId());
    }

    @Override
    public UpdateAddressResponse updateAddress(UpdateAddressRequest addressToUpdate) {
        AddressEntity entityToUpdate = AddressEntity
                .builder()
                .id(addressToUpdate.getId())
                .streetName(addressToUpdate.getStreetName())
                .streetNumber(addressToUpdate.getStreetNumber())
                .zipCode(addressToUpdate.getZipCode())
                .city(addressToUpdate.getCity())
                .country(addressToUpdate.getCountry())
                .phone(addressToUpdate.getPhone())
                .user(UserEntity.builder().id(addressToUpdate.getClientId()).build())
                .build();
        if(addressDal.findById(entityToUpdate.getId()).isEmpty()){
            throw new AddressNotFoundException();
        }
        // validation
        Integer updatedId = addressDal.updateAddress(entityToUpdate);
        return new UpdateAddressResponse(updatedId);
    }

    @Override
    public void deleteAddressWithId(RemoveAddressRequest request) {
        if(addressDal.findById(request.getId()).isEmpty()) {
            throw new AddressNotFoundException();
        }
        addressDal.deleteById(request.getId());
    }
}
