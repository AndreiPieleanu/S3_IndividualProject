package fontys.s3.andreipieleanu.controller;

import fontys.s3.andreipieleanu.domain.requestresponse.login.LoginRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.login.LoginResponse;
import fontys.s3.andreipieleanu.servicelayer.ILoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final ILoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }
}
