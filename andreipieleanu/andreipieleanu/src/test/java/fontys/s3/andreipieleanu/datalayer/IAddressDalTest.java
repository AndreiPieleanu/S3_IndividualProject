package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.AddressEntity;
import fontys.s3.andreipieleanu.datalayer.entities.RoleEnum;
import fontys.s3.andreipieleanu.datalayer.entities.UserEntity;
import fontys.s3.andreipieleanu.datalayer.entities.UserRoleEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IAddressDalTest {
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private IAddressDal addressDal;
    
    @Autowired
    private IUserDal userDal;
    @Autowired
    private IUserRoleDal userRoleDal;
    @Test
    public void createAddressShouldAdd_WhenNewAddressIsAdded(){
        UserRoleEntity role = userRoleDal.findByRole(RoleEnum.CLIENT).orElse(UserRoleEntity
                .builder()
                .role(RoleEnum.CLIENT)
                .build());
        UserEntity userToAdd = UserEntity
                .builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .userRole(role)
                .build();
        UserEntity savedUser = userDal.save(userToAdd);
        assertNotNull(savedUser.getId());
        savedUser = entityManager.find(UserEntity.class, savedUser.getId());
        
        AddressEntity addressToAdd = AddressEntity
                .builder()
                .user(savedUser)
                .phone("123456789")
                .country("Romania")
                .city("Bucharest")
                .zipCode("1234AV")
                .streetNumber(1)
                .streetName("Street")
                .build();
        AddressEntity savedAddress = addressDal.save(addressToAdd);
        assertNotNull(savedAddress.getId());
        savedAddress = entityManager.find(AddressEntity.class,
                savedAddress.getId());
        AddressEntity expectedAddress = AddressEntity
                .builder()
                .id(1)
                .user(savedUser)
                .phone("123456789")
                .country("Romania")
                .city("Bucharest")
                .zipCode("1234AV")
                .streetNumber(1)
                .streetName("Street")
                .build();
        assertEquals(expectedAddress, savedAddress);
    }
    
    @Test
    public void updateAddressStreetShouldUpdate_WhenAddressIsAdded(){
        UserRoleEntity role = userRoleDal.findByRole(RoleEnum.CLIENT).orElse(UserRoleEntity
                .builder()
                .role(RoleEnum.CLIENT)
                .build());
        UserEntity userToAdd = UserEntity
                .builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .userRole(role)
                .build();
        entityManager.persist(userToAdd);
        AddressEntity addressToAdd = AddressEntity
                .builder()
                .user(userToAdd)
                .phone("123456789")
                .country("Romania")
                .city("Bucharest")
                .zipCode("1234AV")
                .streetNumber(1)
                .streetName("Street")
                .build();
        entityManager.persist(addressToAdd);
        AddressEntity updatedAddress = AddressEntity
                .builder()
                .id(1)
                .user(userToAdd)
                .phone("123456789")
                .country("Romania")
                .city("Bucharest")
                .zipCode("1234AV")
                .streetNumber(1)
                .streetName("New Street")
                .build();
        Integer oneAddressUpdated = 1;
        entityManager.flush();
        entityManager.clear();
        
        
        Integer rowsAffected = addressDal.updateAddress(updatedAddress);
        assertNotNull(rowsAffected);
        assertEquals(oneAddressUpdated, rowsAffected);
        AddressEntity retrievedAddress =
                entityManager.find(AddressEntity.class, updatedAddress.getId());
        assertEquals(updatedAddress, retrievedAddress);
    }
    
    @Test
    public void findAddressShouldWork_WhenAddressIsAdded(){
        UserRoleEntity role = userRoleDal.findByRole(RoleEnum.CLIENT).orElse(UserRoleEntity
                .builder()
                .role(RoleEnum.CLIENT)
                .build());
        UserEntity userToAdd = UserEntity
                .builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .userRole(role)
                .build();
        entityManager.persist(userToAdd);
        AddressEntity addressToAdd = AddressEntity
                .builder()
                .user(userToAdd)
                .phone("123456789")
                .country("Romania")
                .city("Bucharest")
                .zipCode("1234AV")
                .streetNumber(1)
                .streetName("Street")
                .build();
        entityManager.persist(addressToAdd);
        entityManager.flush();
        entityManager.clear();
        AddressEntity addedAddress = entityManager.find(AddressEntity.class,
                addressToAdd.getId());
        Optional<AddressEntity> foundAddress =
                addressDal.findById(addedAddress.getId());
        assertTrue(foundAddress.isPresent());
        assertEquals(addedAddress, foundAddress.get());
    }
    
    @Test
    public void findAddressShouldReturnNull_WhenAddressIsNotAdded(){
        Optional<AddressEntity> foundAddress = addressDal.findById(1);
        assertTrue(foundAddress.isEmpty());
    }
    
    @Test
    public void deleteAddressShouldDeleteIt_WhenAddressIsAdded(){
        UserRoleEntity role = userRoleDal.findByRole(RoleEnum.CLIENT).orElse(UserRoleEntity
                .builder()
                .role(RoleEnum.CLIENT)
                .build());
        UserEntity userToAdd = UserEntity
                .builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .userRole(role)
                .build();
        entityManager.persist(userToAdd);
        AddressEntity addressToAdd = AddressEntity
                .builder()
                .user(userToAdd)
                .phone("123456789")
                .country("Romania")
                .city("Bucharest")
                .zipCode("1234AV")
                .streetNumber(1)
                .streetName("Street")
                .build();
        entityManager.persist(addressToAdd);
        entityManager.flush();
        entityManager.clear();
        AddressEntity foundAddress = entityManager.find(AddressEntity.class,
                addressToAdd.getId());
        
        addressDal.deleteById(foundAddress.getId());
        
        Optional<AddressEntity> possibleAddress =
                addressDal.findById(foundAddress.getId());
        assertTrue(possibleAddress.isEmpty());
    }
}