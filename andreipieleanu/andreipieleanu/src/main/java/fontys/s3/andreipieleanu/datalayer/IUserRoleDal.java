package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.RoleEnum;
import fontys.s3.andreipieleanu.datalayer.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRoleDal extends JpaRepository<UserRoleEntity, Integer> {
    Optional<UserRoleEntity> findByRole(RoleEnum role);
}
