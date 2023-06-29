package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.IAddressDal;
import fontys.s3.andreipieleanu.datalayer.entities.AddressEntity;
import fontys.s3.andreipieleanu.datalayer.entities.UserEntity;
import fontys.s3.andreipieleanu.domain.requestresponse.address.*;
import fontys.s3.andreipieleanu.servicelayer.IAddressService;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.AddressNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TestAddressService {
    private IAddressDal addressDal;
    private IAddressService addressService;
    @BeforeEach
    public void setUp(){
        addressDal = mock(IAddressDal.class);
        addressService = new AddressService(addressDal);
    }
    @Test
    public void addAddressShouldWork_WhenAddressIsNotThere(){
        // Arrange 
        CreateAddressRequest request = new CreateAddressRequest("street", 123, "zipcode", "city", "country", "123456789", 1);
        AddressEntity savedEntity = AddressEntity.builder().id(1).build();
        when(addressDal.save(any(AddressEntity.class))).thenReturn(savedEntity);

        // Act
        CreateAddressResponse response = addressService.createAddress(request);

        // Assert
        assertEquals(savedEntity.getId(), response.getId());
    }
    @Test
    public void updateAddressShouldWork_WhenAddressIsThere() {
        // Arrange
        AddressEntity updatableAddress = AddressEntity.builder()
                .id(1).streetName("old street name").streetNumber(123).zipCode("old zipcode")
                .city("old city").country("old country").phone("1234567890")
                .user(UserEntity.builder().id(1).build())
                .build();
        UpdateAddressRequest updateAddressRequest = new UpdateAddressRequest(
                1, "new street name", 456, "new zipcode", "new city", "new country", "0987654321", 1);
        when(addressDal.updateAddress(any(AddressEntity.class))).thenReturn(1);
        when(addressDal.findById(1)).thenReturn(Optional.of(updatableAddress));
        // Act
        UpdateAddressResponse response = addressService.updateAddress(updateAddressRequest);

        // Assert
        assertEquals(updateAddressRequest.getId(), response.getId());
    }
    @Test
    public void updateAddressShouldThrowException_WhenAddressIsNotFound(){
        // Arrange
        AddressEntity nonExistentAddress =
                AddressEntity.builder().id(1).build();
        when(addressDal.findById(nonExistentAddress.getId())).thenReturn(Optional.empty());
        RemoveAddressRequest request =
                new RemoveAddressRequest(nonExistentAddress.getId());
        // Act
        Throwable thrown = assertThrows(AddressNotFoundException.class, () -> addressService.deleteAddressWithId(request));
        // Assert
        assertNotNull(thrown);
        assertEquals("404 NOT_FOUND \"ADDRESS COULD NOT BE FOUND\"", thrown.getMessage());
    }
    @Test
    public void removeAddressWithId1ShouldRemove__WhenAddressIsAdded(){
        // Arrange
        UserEntity user = UserEntity.builder().id(1).build();
        AddressEntity savedAddress = AddressEntity
                .builder()
                .id(1)
                .country("country")
                .phone("123456789")
                .user(user)
                .streetNumber(123)
                .streetName("street")
                .city("city")
                .build();
        when(addressDal.findById(savedAddress.getId())).thenReturn(Optional.of(savedAddress));
        RemoveAddressRequest request = new RemoveAddressRequest(1);
        // Act 
        addressService.deleteAddressWithId(request);
        // Assert 
        verify(addressDal).deleteById(savedAddress.getId());
    }
    @Test
    public void removeAddressWithIdShouldThrowException_WhenAddressIsNotAdded(){
        // Arrange
        when(addressDal.findById(1)).thenReturn(Optional.empty());
        RemoveAddressRequest request = new RemoveAddressRequest(1);
        // Act
        Throwable thrown = assertThrows(AddressNotFoundException.class,
                () -> addressService.deleteAddressWithId(request));
        // Assert 
        assertNotNull(thrown);
        assertEquals("404 NOT_FOUND \"ADDRESS COULD NOT BE FOUND\"", thrown.getMessage());
    }
    @Test
    public void getAllAddressesOfUserWithId1ShouldWork_WhenAddressesAreAdded(){
        // Arrange 
        UserEntity user = UserEntity.builder().id(1).build();
        AddressEntity addressToAdd1 = AddressEntity
                .builder()
                .id(1)
                .country("country1")
                .phone("123456789")
                .user(user)
                .streetNumber(123)
                .streetName("street1")
                .city("city1")
                .build();
        AddressEntity addressToAdd2 = AddressEntity
                .builder()
                .id(2)
                .country("country2")
                .phone("123456789")
                .user(user)
                .streetNumber(123)
                .streetName("street2")
                .city("city2")
                .build();
        when(addressDal.findAll()).thenReturn(List.of(addressToAdd1,
                addressToAdd2));
        GetAddressRequest request = new GetAddressRequest(1);
        Integer expectedListSize = 2;
        // Act 
        GetAddressResponse response =
                addressService.getAllAddressesOfUserWithId(request);
        // Assert 
        assertNotNull(response);
        assertEquals(expectedListSize, response.getFoundAddresses().size());
    }
}
