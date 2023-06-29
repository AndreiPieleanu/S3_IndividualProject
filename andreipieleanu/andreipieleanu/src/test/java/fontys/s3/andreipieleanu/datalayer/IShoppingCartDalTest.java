package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IShoppingCartDalTest {
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private IShoppingCartDal shoppingCartDal;
    
    @Autowired
    private IUserRoleDal userRoleDal;

    @Test
    public void emptyCart_WhenItemIsAdded() {
        UserRoleEntity role = userRoleDal.findByRole(RoleEnum.CLIENT).orElse(UserRoleEntity
                .builder()
                .role(RoleEnum.CLIENT)
                .build());
        UserEntity userToAdd = UserEntity
                .builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .userRole(role)
                .build();
        entityManager.persist(userToAdd);
        
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name("T-Shirts")
                .build();
        entityManager.persist(categoryEntity);
        
        ClothesEntity clothesToAdd = ClothesEntity
                .builder()
                .sizesEntity(SizesEntity.S)
                .name("T-Shirt")
                .description("A T-shirt")
                .price(9.99)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(categoryEntity)
                .isActive(true)
                .amountInStock(100)
                .build();
        entityManager.persist(clothesToAdd);
        
        ShoppingCartEntity shoppingCartEntity =
                ShoppingCartEntity
                        .builder()
                        .user(userToAdd)
                        .cartItems(new HashMap<>())
                        .build();
        entityManager.persist(shoppingCartEntity);
        
        CartItemEntity product = CartItemEntity
                .builder()
                .item(clothesToAdd)
                .shoppingCartEntity(shoppingCartEntity)
                .amount(10)
                .build();
        entityManager.persist(product);
        
        entityManager.flush();
        entityManager.clear();
        shoppingCartDal.emptyCartWithId(shoppingCartEntity.getId());
        
        assertTrue(shoppingCartEntity.getCartItems().isEmpty());
    }
    @Test
    public void createCartShouldWork_WhenCartIsNew(){
        UserRoleEntity role = userRoleDal.findByRole(RoleEnum.CLIENT).orElse(UserRoleEntity
                .builder()
                .role(RoleEnum.CLIENT)
                .build());
        UserEntity userToAdd = UserEntity
                .builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .password("basdyuvwtuvvawtuv")
                .userRole(role)
                .build();
        entityManager.persist(userToAdd);

        ShoppingCartEntity shoppingCartEntity =
                ShoppingCartEntity
                        .builder()
                        .user(userToAdd)
                        .cartItems(new HashMap<>())
                        .build();
        entityManager.persist(shoppingCartEntity);

        entityManager.flush();
        entityManager.clear();
        
        ShoppingCartEntity foundCart =
                entityManager.find(ShoppingCartEntity.class,
                        shoppingCartEntity.getId());
        ShoppingCartEntity expectedCart =
                ShoppingCartEntity
                        .builder()
                        .id(2)
                        .user(userToAdd)
                        .cartItems(new HashMap<>())
                        .build();
        assertNotNull(foundCart);
        assertNotNull(foundCart.getId());
        assertEquals(expectedCart, foundCart);
    }
}