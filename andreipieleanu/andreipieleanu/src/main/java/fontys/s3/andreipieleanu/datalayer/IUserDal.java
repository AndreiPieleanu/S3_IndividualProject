package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface IUserDal extends JpaRepository<UserEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update UserEntity us set us.email=:#{#userEntity.email}, " +
            "us.firstName=:#{#userEntity.firstName}, " +
            "us.lastName=:#{#userEntity.lastName}, " +
            "us.password=:#{#userEntity.password} where us.id=:#{#userEntity.id}")
    Integer updateUser(UserEntity userEntity);
    @Query("select us from UserEntity us where us.email=:email")
    UserEntity findByEmail(@Param("email") String email);
}
