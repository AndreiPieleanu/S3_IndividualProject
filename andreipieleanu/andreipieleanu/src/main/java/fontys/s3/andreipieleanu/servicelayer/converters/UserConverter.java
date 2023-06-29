package fontys.s3.andreipieleanu.servicelayer.converters;

import fontys.s3.andreipieleanu.datalayer.entities.UserEntity;
import fontys.s3.andreipieleanu.datalayer.entities.UserRoleEntity;
import fontys.s3.andreipieleanu.domain.User;
import fontys.s3.andreipieleanu.domain.UserRole;

public final class UserConverter {
    private UserConverter(){}
    public static User convert(UserEntity entity){
        return User
                .builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .userRole(UserRole.builder().id(entity.getUserRole().getId()).build())
                .build();
    }
    public static UserEntity convert(User user){
        return UserEntity
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userRole(UserRoleEntity.builder().id(user.getUserRole().getId()).build())
                .build();
    }
}
