package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.IShoppingCartDal;
import fontys.s3.andreipieleanu.datalayer.IUserDal;
import fontys.s3.andreipieleanu.datalayer.IUserRoleDal;
import fontys.s3.andreipieleanu.datalayer.entities.RoleEnum;
import fontys.s3.andreipieleanu.datalayer.entities.ShoppingCartEntity;
import fontys.s3.andreipieleanu.datalayer.entities.UserEntity;
import fontys.s3.andreipieleanu.datalayer.entities.UserRoleEntity;
import fontys.s3.andreipieleanu.domain.User;
import fontys.s3.andreipieleanu.domain.requestresponse.user.*;
import fontys.s3.andreipieleanu.servicelayer.IUserService;
import fontys.s3.andreipieleanu.servicelayer.converters.UserConverter;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.DuplicatedUserException;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestUserService {
    IUserDal userDal = mock(IUserDal.class);
    IShoppingCartDal shoppingCartDal = mock(IShoppingCartDal.class);
    IUserRoleDal userRoleDal = mock(IUserRoleDal.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    IUserService userService = new UserService(userDal, shoppingCartDal,
            userRoleDal, passwordEncoder);
    @Test
    public void createNewUserSuccessfully_WhenNoSameUserIsAdded() {
        // given
        CreateNewUserRequest request = new CreateNewUserRequest("test@example.com", "John", "Doe", "password");
        UserEntity savedUserEntity = UserEntity.builder()
                .id(1)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .userRole(UserRoleEntity.builder().id(1).role(RoleEnum.CLIENT).build())
                .password("password")
                .build();
        when(userDal.findAll()).thenReturn(Collections.emptyList());
        when(userDal.save(any(UserEntity.class))).thenReturn(savedUserEntity);
        when(shoppingCartDal.save(any(ShoppingCartEntity.class))).thenReturn(null);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(userRoleDal.findById(RoleEnum.CLIENT.getValue())).thenReturn(Optional.of(UserRoleEntity.builder().id(1).role(RoleEnum.CLIENT).build()));
        // when
        CreateNewUserResponse response = userService.createNewClientAccount(request);

        // then
        assertEquals("User has been created successfully!", response.getMessage());
        verify(userDal).save(any(UserEntity.class));
        verify(shoppingCartDal).save(any(ShoppingCartEntity.class));
    }
    @Test
    public void createNewUserShouldThrowException_WhenSameUserIsAlreadyAdded() {
        // Arrange
        CreateNewUserRequest request = new CreateNewUserRequest();
        request.setEmail("johndoe@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("password123");

        UserEntity existingUser = UserEntity.builder()
                .id(1)
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .userRole(UserRoleEntity.builder().id(1).role(RoleEnum.CLIENT).build())
                .build();
        List<UserEntity> userList = new ArrayList<>();
        userList.add(existingUser);

        when(userDal.findAll()).thenReturn(userList);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(userRoleDal.findById(RoleEnum.CLIENT.getValue())).thenReturn(Optional.of(UserRoleEntity.builder().id(1).role(RoleEnum.CLIENT).build()));
        // Act
        Throwable thrown = assertThrows(DuplicatedUserException.class, () -> userService.createNewClientAccount(request));

        // Assert
        assertNotNull(thrown);
        assertEquals("422 UNPROCESSABLE_ENTITY \"USER WITH SUCH CREDENTIALS ALREADY EXISTS!\"", thrown.getMessage());
        assertEquals(1, userList.size());
    }
    @Test
    public void findUserShouldRetrieveUser_WhenUserExists() {
        // given
        int userId = 1;
        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .userRole(UserRoleEntity.builder().id(1).role(RoleEnum.CLIENT).build())
                .password("password")
                .build();
        when(userDal.findById(userId)).thenReturn(Optional.of(userEntity));

        // when
        FindUserResponse response = userService.findUser(new FindUserRequest(userId));

        // then
        User expectedUser = UserConverter.convert(userEntity);
        assertEquals(expectedUser, response.getFoundUser());
        verify(userDal).findById(userId);
    }
    @Test
    public void updateUserShouldThrowException_WhenUserNotFound() {
        // given
        int userId = 1;
        when(userDal.findById(userId)).thenReturn(Optional.empty());
        when(userRoleDal.findById(1)).thenReturn(Optional.of(UserRoleEntity.builder().id(1).role(RoleEnum.CLIENT).build()));
        UpdateUserRequest request = new UpdateUserRequest(userId, "test@example.com", "Jane", "Doe", "password");

        // when
        Throwable thrown  = assertThrows((UserNotFoundException.class), () -> userService.updateUser(request));
        verify(userDal, never()).updateUser(any(UserEntity.class));
        assertEquals("404 NOT_FOUND \"USER COULD NOT BE FOUND\"", thrown.getMessage());
        // then - expect UserNotFoundException to be thrown
    }
}