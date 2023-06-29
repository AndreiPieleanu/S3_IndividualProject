package fontys.s3.andreipieleanu.datalayer;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IUserDalTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private IUserDal userDal;
    
    @Autowired
    private IUserRoleDal userRoleDal;

    @Test
    public void save_shouldSaveUser() {
        UserRoleEntity role = userRoleDal.findByRole(RoleEnum.CLIENT).orElse(UserRoleEntity
                .builder()
                .role(RoleEnum.CLIENT)
                .build());
        
        UserEntity userToAdd = UserEntity
                .builder()
                .firstName("John")
                .lastName("Doe")
                .userRole(role)
                .email("johndoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .build();
        UserEntity savedUser = userDal.save(userToAdd);
        assertNotNull(savedUser.getId());

        savedUser = entityManager.find(UserEntity.class, savedUser.getId());
        UserEntity expectedUser = UserEntity
                .builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .userRole(role)
                .email("johndoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .build();
        assertEquals(expectedUser, savedUser);
    }

    @Test
    public void findUserShouldRetrieveUser_WhenUserExists() {
        UserRoleEntity role = userRoleDal.findByRole(RoleEnum.CLIENT).orElse(UserRoleEntity
                .builder()
                .role(RoleEnum.CLIENT)
                .build());

        UserEntity userToAdd = UserEntity
                .builder()
                .firstName("John")
                .lastName("Doe")
                .userRole(role)
                .email("johndoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .build();
        entityManager.persist(userToAdd);

        entityManager.flush();
        entityManager.clear();

        UserEntity expectedUser = UserEntity
                .builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .userRole(UserRoleEntity.builder().id(1).role(RoleEnum.CLIENT).build())
                .email("johndoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .build();
        UserEntity foundUser = entityManager.find(UserEntity.class,
                userToAdd.getId());
        assertNotNull(foundUser);
        assertEquals(expectedUser, foundUser);
    }
    
    @Test
    public void findUserShouldReturnNull_WhenUserDoesNotExist(){
        UserEntity expectedUser = UserEntity
                .builder()
                .id(5)
                .build();
        UserEntity foundUser = entityManager.find(UserEntity.class,
                expectedUser.getId());
        assertNull(foundUser);
    }
    @Test
    public void updateUserShouldUpdate_WhenUserIsAdded(){
        UserRoleEntity role = userRoleDal.findByRole(RoleEnum.CLIENT).orElse(UserRoleEntity
                .builder()
                .role(RoleEnum.CLIENT)
                .build());

        UserEntity userToAdd = UserEntity
                .builder()
                .firstName("John")
                .lastName("Doe")
                .userRole(role)
                .email("johndoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .build();
        userToAdd = userDal.save(userToAdd);
        
        entityManager.flush();
        entityManager.clear();
        
        UserEntity updatedUser = UserEntity
                .builder()
                .id(userToAdd.getId())
                .firstName("Jack")
                .lastName("Doe")
                .userRole(role)
                .email("jackdoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .build();
        
        Integer updatedId = userDal.updateUser(updatedUser);
        UserEntity foundUser = entityManager.find(UserEntity.class, userToAdd.getId());
        assertNotNull(updatedId);
        assertEquals(updatedUser, foundUser);
    }
    
    @Test
    public void updateUserShouldReturn0_WhenUserDoesNotExist(){
        UserRoleEntity role = userRoleDal.findByRole(RoleEnum.CLIENT).orElse(UserRoleEntity
                .builder()
                .role(RoleEnum.CLIENT)
                .build());
        UserEntity updatedUser = UserEntity
                .builder()
                .id(5)
                .firstName("Jack")
                .lastName("Doe")
                .userRole(role)
                .email("jackdoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .build();
        Integer noRowsAffected = 0;

        Integer updatedId = userDal.updateUser(updatedUser);
        
        assertEquals(noRowsAffected, updatedId);
    }
    
    @Test
    public void findAllUserShouldReturn4_When2MoreUsersAreAdded(){
        UserRoleEntity role = userRoleDal.findByRole(RoleEnum.CLIENT).orElse(UserRoleEntity
                .builder()
                .role(RoleEnum.CLIENT)
                .build());
        
        UserEntity userToAdd1 = UserEntity
                .builder()
                .firstName("John")
                .lastName("Doe")
                .userRole(role)
                .email("johndoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .build();
        UserEntity userToAdd2 = UserEntity
                .builder()
                .firstName("Jimmy")
                .lastName("Hendrix")
                .userRole(role)
                .email("jimmyhendrix@gmail.com")
                .password("djsabdibasdbuw")
                .build();
        Integer expectedNumberOfUsersInList = 4;
        entityManager.persist(userToAdd1);
        entityManager.persist(userToAdd2);
        
        entityManager.flush();
        entityManager.clear();

        List<UserEntity> foundUsers = userDal.findAll();
        assertNotNull(foundUsers);
        assertEquals(expectedNumberOfUsersInList, foundUsers.size());
        assertEquals(userToAdd1, foundUsers.get(2));
        assertEquals(userToAdd2, foundUsers.get(3));
    }
    
    @Test
    public void findUserShouldRetrieve2Users_WhenOnlyPreUsersAreAdded(){
        // Arrange - i pre-added 2 admin users in the db
        Integer expectedNumberOfUsersInList = 2;
        List<UserEntity> foundUsers = userDal.findAll();
        assertNotNull(foundUsers);
        assertEquals(expectedNumberOfUsersInList, foundUsers.size());
    }
}