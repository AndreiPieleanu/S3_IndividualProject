package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface IShoppingCartDal extends JpaRepository<ShoppingCartEntity, Integer> {
    @Transactional
    @Modifying
    @Query("delete from CartItemEntity item where item.shoppingCartEntity" +
            ".id=:cartId")
    void emptyCartWithId(@Param("cartId") Integer cartId);
    @Query("select cart from ShoppingCartEntity cart where cart.user.id=:userId")
    Optional<ShoppingCartEntity> getShoppingCartBasedOnUserId(@Param("userId")Integer userId);
}
