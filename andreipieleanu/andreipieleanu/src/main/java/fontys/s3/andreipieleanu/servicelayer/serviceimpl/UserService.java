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
import fontys.s3.andreipieleanu.servicelayer.customexceptions.UserRoleNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private IUserDal userDal;
    private IShoppingCartDal shoppingCartDal;
    private IUserRoleDal userRoleDal;
    private final PasswordEncoder passwordEncoder;
    @Override
    public GetAllUsersResponse getAllUsers() {
        List<UserEntity> allUsers = userDal.findAll();
        List<User> convertedUsers = new ArrayList<>();
        allUsers.forEach(
                userEntity -> convertedUsers.add(UserConverter.convert(userEntity))
        );
        return new GetAllUsersResponse(convertedUsers);
    }

    @Override
    @Transactional
    public CreateNewUserResponse createNewClientAccount(CreateNewUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        
        UserRoleEntity userRole = userRoleDal.findById(RoleEnum.CLIENT.getValue())
                .orElseThrow(UserRoleNotFoundException::new);
        
        UserEntity userToCreate = UserEntity
                .builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(encodedPassword)
                .userRole(userRole)
                .build();
        if(userDal.findAll().contains(userToCreate)){
            throw new DuplicatedUserException();
        }
        userDal.save(userToCreate);
        shoppingCartDal.save(ShoppingCartEntity.builder().user(userToCreate).cartItems(new HashMap<>()).build());
        return new CreateNewUserResponse("User has been created successfully!");
    }

    @Override
    public FindUserResponse findUser(FindUserRequest request) {
        Optional<UserEntity> foundEntity = userDal.findById(request.getUserId());
        Optional<User> convertedUser;
        if(foundEntity.isPresent()) {
            convertedUser = Optional.of(UserConverter.convert(foundEntity.get()));
        }
        else{
            throw new UserNotFoundException();
        }
        return new FindUserResponse(convertedUser.get());
    }

    @Transactional
    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserRoleEntity userRole = userRoleDal.findById(RoleEnum.CLIENT.getValue())
                .orElseThrow(UserRoleNotFoundException::new);
        
        UserEntity userToUpdate = UserEntity
                .builder()
                .id(request.getUserId())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(encodedPassword)
                .userRole(userRole)
                .build();
        
        if(userDal.findById(request.getUserId()).isEmpty()){
            throw new UserNotFoundException();
        }
        Integer updatedEntityId = userDal.updateUser(userToUpdate);
        return new UpdateUserResponse(updatedEntityId);
    }
}
