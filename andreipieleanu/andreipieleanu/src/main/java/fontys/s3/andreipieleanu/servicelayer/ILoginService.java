package fontys.s3.andreipieleanu.servicelayer;

import fontys.s3.andreipieleanu.domain.requestresponse.login.LoginRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.login.LoginResponse;

public interface ILoginService {
    LoginResponse login(LoginRequest loginRequest);
}
