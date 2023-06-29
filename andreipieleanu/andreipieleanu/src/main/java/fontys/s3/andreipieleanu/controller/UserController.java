package fontys.s3.andreipieleanu.controller;

import fontys.s3.andreipieleanu.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.andreipieleanu.domain.requestresponse.user.*;
import fontys.s3.andreipieleanu.servicelayer.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private IUserService userService;
    @GetMapping
    @IsAuthenticated
    @RolesAllowed({"ROLE_WORKER"})
    public ResponseEntity<GetAllUsersResponse> getAllUsers(){
        GetAllUsersResponse response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }
    @GetMapping("{userId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_CLIENT", "ROLE_WORKER"})
    public ResponseEntity<FindUserResponse> getUserById(@PathVariable(name = 
            "userId")int userId){
        FindUserRequest request = new FindUserRequest(userId);
        return ResponseEntity.ok(userService.findUser(request));
    }
    @PostMapping
    public ResponseEntity<CreateNewUserResponse> createNewUser(@RequestBody CreateNewUserRequest request){
        return ResponseEntity.ok(userService.createNewClientAccount(request));
    }
    @PutMapping("{userId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_CLIENT"})
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable(name =
            "userId")int userId, @RequestBody UpdateUserRequest request){
        request.setUserId(userId);
        return ResponseEntity.ok(userService.updateUser(request));
    }
}
