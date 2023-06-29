package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.IUserDal;
import fontys.s3.andreipieleanu.datalayer.entities.UserEntity;
import fontys.s3.andreipieleanu.domain.AccessToken;
import fontys.s3.andreipieleanu.domain.requestresponse.login.LoginRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.login.LoginResponse;
import fontys.s3.andreipieleanu.servicelayer.IAccessTokenEncoder;
import fontys.s3.andreipieleanu.servicelayer.ILoginService;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginService implements ILoginService {
    private final IUserDal userDal;
    private final PasswordEncoder passwordEncoder;
    private final IAccessTokenEncoder accessTokenEncoder;
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user = userDal.findByEmail(loginRequest.getEmail());
        if(user == null) {
            throw new InvalidCredentialsException();
        }
        if(!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        
        String accessToken = generateAccessToken(user);
        return LoginResponse.builder().accessToken(accessToken).build();
    }
    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    private String generateAccessToken(UserEntity entity){
        Integer userId = null;
        if(entity.getId() != null){
            userId = entity.getId();
        }

        String userRole = entity.getUserRole().getRole().toString();
        
        return accessTokenEncoder.encode(
                AccessToken
                        .builder()
                        .subject(entity.getEmail())
                        .role(userRole)
                        .userId(userId)
                        .build()
        );
    }
}
