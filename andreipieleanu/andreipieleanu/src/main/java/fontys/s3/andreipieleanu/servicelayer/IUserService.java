package fontys.s3.andreipieleanu.servicelayer;

import fontys.s3.andreipieleanu.domain.requestresponse.user.*;


public interface IUserService {
    GetAllUsersResponse getAllUsers();
    CreateNewUserResponse createNewClientAccount(CreateNewUserRequest request);
    FindUserResponse findUser(FindUserRequest request);
    UpdateUserResponse updateUser(UpdateUserRequest request);
}
