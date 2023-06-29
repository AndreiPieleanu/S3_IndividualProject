package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.IUserDal;
import fontys.s3.andreipieleanu.datalayer.entities.RoleEnum;
import fontys.s3.andreipieleanu.datalayer.entities.UserEntity;
import fontys.s3.andreipieleanu.datalayer.entities.UserRoleEntity;
import fontys.s3.andreipieleanu.domain.AccessToken;
import fontys.s3.andreipieleanu.domain.Role;
import fontys.s3.andreipieleanu.domain.User;
import fontys.s3.andreipieleanu.domain.UserRole;
import fontys.s3.andreipieleanu.domain.requestresponse.login.LoginRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.login.LoginResponse;
import fontys.s3.andreipieleanu.servicelayer.IAccessTokenEncoder;
import fontys.s3.andreipieleanu.servicelayer.ILoginService;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    private IUserDal userDal;
    private PasswordEncoder passwordEncoder;
    private IAccessTokenEncoder encoder;
    private ILoginService loginService;

    private final UserEntity userEntity =
            UserEntity
                    .builder()
                    .id(1)
                    .userRole(UserRoleEntity.builder().id(1).role(RoleEnum.CLIENT).build())
                    .email("jdoe@gmail.com")
                    .password("jdoe123")
                    .firstName("John")
                    .lastName("Doe")
                    .build();
    private final User user =
            User
                    .builder()
                    .id(1)
                    .userRole(UserRole.builder().id(1).role(Role.CLIENT).build())
                    .email("jdoe@gmail.com")
                    .password("jdoe123")
                    .firstName("John")
                    .lastName("Doe")
                    .build();
    
    @BeforeEach
    public void setUp(){
        userDal = mock(IUserDal.class);
        passwordEncoder = mock(PasswordEncoder.class);
        encoder = mock(IAccessTokenEncoder.class);
        loginService = new LoginService(userDal, passwordEncoder, encoder);
    }
    
    @Test
    public void findUserByEmailShouldThrowInvalidCredentialsException_WhenEmailIsInvalid(){
        // Arrange 
        LoginRequest invalidEmailRequest = new LoginRequest("wrongemail", 
                "jdoe123");
        when(userDal.findByEmail(invalidEmailRequest.getEmail())).thenReturn(null);
        // Act 
        Throwable throwable = assertThrows(InvalidCredentialsException.class,
                () -> loginService.login(invalidEmailRequest));
        // Assert 
        assertNotNull(throwable);
        assertEquals("400 BAD_REQUEST \"INVALID_CREDENTIALS\"", throwable.getMessage());
    }
    @Test
    public void loginShouldThrowException_WhenPasswordIsIncorrect(){
        // Arrange 
        LoginRequest invalidPasswordRequest = new LoginRequest("jdoe@gmail.com",
                "wrong password");
        when(userDal.findByEmail(invalidPasswordRequest.getEmail())).thenReturn(userEntity);
        when(passwordEncoder.matches(invalidPasswordRequest.getPassword(), user.getPassword())).thenReturn(false);
        // Act 
        Throwable throwable = assertThrows(InvalidCredentialsException.class,
                () -> loginService.login(invalidPasswordRequest));
        // Assert 
        assertNotNull(throwable);
        assertEquals("400 BAD_REQUEST \"INVALID_CREDENTIALS\"", throwable.getMessage());
    }
    @Test
    public void loginShouldReturnToken_WhenUserExists(){
        // Arrange 
        LoginRequest goodRequest = new LoginRequest("jdoe@gmail.com",
                "jdoe123");
        when(userDal.findByEmail(goodRequest.getEmail())).thenReturn(userEntity);
        when(passwordEncoder.matches(goodRequest.getPassword(), user.getPassword())).thenReturn(true);        
        when(encoder.encode(any(AccessToken.class))).thenReturn("Bearer encoded");
        // Act 
        LoginResponse response = loginService.login(goodRequest);
        // Assert 
        assertNotNull(response);
        assertEquals("Bearer encoded", response.getAccessToken());
    }
}