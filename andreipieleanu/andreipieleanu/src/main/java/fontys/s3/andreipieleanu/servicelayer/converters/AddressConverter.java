package fontys.s3.andreipieleanu.servicelayer.converters;

import fontys.s3.andreipieleanu.datalayer.entities.AddressEntity;
import fontys.s3.andreipieleanu.datalayer.entities.UserEntity;
import fontys.s3.andreipieleanu.domain.Address;
import fontys.s3.andreipieleanu.domain.User;

public final class AddressConverter {
    private AddressConverter(){}
    public static Address convert(AddressEntity addressEntity){
        return Address
                .builder()
                .id(addressEntity.getId())
                .streetName(addressEntity.getStreetName())
                .streetNumber(addressEntity.getStreetNumber())
                .city(addressEntity.getCity())
                .country(addressEntity.getCountry())
                .zipCode(addressEntity.getZipCode())
                .phone(addressEntity.getPhone())
                .user(User.builder().id(addressEntity.getUser().getId()).build())
                .build();
    }
    
    public static AddressEntity convert(Address address){
        return AddressEntity
                .builder()
                .id(address.getId())
                .streetName(address.getStreetName())
                .streetNumber(address.getStreetNumber())
                .city(address.getCity())
                .country(address.getCountry())
                .zipCode(address.getZipCode())
                .phone(address.getPhone())
                .user(UserEntity.builder().id(address.getUser().getId()).build())
                .build();
    }
}
