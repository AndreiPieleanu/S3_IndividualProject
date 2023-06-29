package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface IShoppingCartItemDal extends JpaRepository<CartItemEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update CartItemEntity ent set ent.amount=:#{#entityToUpdate.amount} where ent.id=:#{#entityToUpdate.id}")
    Integer editItem(CartItemEntity entityToUpdate);
    @Transactional
    @Modifying
    @Query(value = "delete from CartItemEntity it where it.item.id=:itemId " +
            "and it.shoppingCartEntity.id=:cartId")
    void removeItem(@Param("itemId") Integer itemId,
                    @Param("cartId") Integer cartId);
}
