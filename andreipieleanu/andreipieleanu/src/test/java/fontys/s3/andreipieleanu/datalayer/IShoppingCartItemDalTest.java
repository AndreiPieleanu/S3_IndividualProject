package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.*;
import org.junit.jupiter.api.BeforeEach;
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
public class IShoppingCartItemDalTest {
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private IShoppingCartItemDal shoppingCartItemDal;

    @Autowired
    private IUserRoleDal userRoleDal;
    private ClothesEntity clothesToAdd;
    private ClothesEntity otherClothesToAdd;
    private ShoppingCartEntity shoppingCartEntity;
    @BeforeEach
    public void setUp(){
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

        clothesToAdd = ClothesEntity
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

        otherClothesToAdd = ClothesEntity
                .builder()
                .sizesEntity(SizesEntity.S)
                .name("Skirt")
                .description("A Skirt")
                .price(19.99)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(categoryEntity)
                .isActive(true)
                .amountInStock(100)
                .build();
        entityManager.persist(otherClothesToAdd);

        shoppingCartEntity =
                ShoppingCartEntity
                        .builder()
                        .user(userToAdd)
                        .cartItems(new HashMap<>())
                        .build();
        entityManager.persist(shoppingCartEntity);
    }
    
    @Test
    public void createCartItemShouldWork_WhenItemDoesNotExist(){
        CartItemEntity product = CartItemEntity
                .builder()
                .item(clothesToAdd)
                .shoppingCartEntity(shoppingCartEntity)
                .amount(10)
                .build();
        entityManager.persist(product);

        entityManager.flush();
        entityManager.clear();

        CartItemEntity actualItem = shoppingCartItemDal.findById(product.getId()).orElse(null);

        assertNotNull(actualItem);
        assertNotNull(actualItem.getId());
        assertEquals(product.getAmount(), actualItem.getAmount());
        assertEquals(product.getItem(), actualItem.getItem());
    }
    
    @Test
    public void editItemShouldReturn1RowsAffected_When2ItemsAreAdded(){
        CartItemEntity product1 = CartItemEntity
                .builder()
                .item(clothesToAdd)
                .shoppingCartEntity(shoppingCartEntity)
                .amount(10)
                .build();
        entityManager.persist(product1);

        CartItemEntity product2 = CartItemEntity
                .builder()
                .item(otherClothesToAdd)
                .shoppingCartEntity(shoppingCartEntity)
                .amount(10)
                .build();
        entityManager.persist(product2);

        entityManager.flush();
        entityManager.clear();
        
        CartItemEntity itemEdited = CartItemEntity
                .builder()
                .id(product2.getId())
                .item(otherClothesToAdd)
                .shoppingCartEntity(shoppingCartEntity)
                .amount(20)
                .build();
        Integer affectedRows = shoppingCartItemDal.editItem(itemEdited);
        CartItemEntity actualItem = shoppingCartItemDal.findById(product2.getId()).orElse(null);
        
        assertEquals(1, affectedRows);
        assertNotNull(actualItem);
        assertEquals(itemEdited.getAmount(), actualItem.getAmount());
    }
    
    @Test
    public void removeItemFromCartShouldRemove_WhenItemExists(){
        CartItemEntity product1 = CartItemEntity
                .builder()
                .item(clothesToAdd)
                .shoppingCartEntity(shoppingCartEntity)
                .amount(10)
                .build();
        entityManager.persist(product1);

        entityManager.flush();
        entityManager.clear();
        
        shoppingCartItemDal.removeItem(product1.getItem().getId(),
                shoppingCartEntity.getId());
        CartItemEntity actualItem = shoppingCartItemDal.findById(product1.getId()).orElse(null);
        
        assertTrue(shoppingCartEntity.getCartItems().isEmpty());
        assertNull(actualItem);
    }
}