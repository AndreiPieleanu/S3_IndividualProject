package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.IShoppingCartDal;
import fontys.s3.andreipieleanu.datalayer.entities.*;
import fontys.s3.andreipieleanu.domain.*;
import fontys.s3.andreipieleanu.domain.requestresponse.cart.*;
import fontys.s3.andreipieleanu.servicelayer.IShoppingCartService;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.CartNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TestShoppingCart {
    private IShoppingCartDal cartDal;
    private IShoppingCartService shoppingCartService;
    @BeforeEach
    public void setUp(){
        cartDal = mock(IShoppingCartDal.class);
        shoppingCartService = new ShoppingCartService(cartDal);
    }
    @Test
    public void getCardShouldReturnCartWithId1_whenCartExists(){
        // Arrange 
        ShoppingCartEntity shoppingCartEntity =
                ShoppingCartEntity.builder().id(1).build();
        Map<Integer, CartItemEntity> cartItems = new HashMap<>();
        cartItems.put(1, 
                CartItemEntity.builder().id(1).item(
                        ClothesEntity
                                .builder()
                                .id(1)
                                .sizesEntity(SizesEntity.S)
                                .subcategoryEntity(CategoryEntity.builder().id(1).build())
                                .build()
                ).amount(10).build());
        shoppingCartEntity.setCartItems(cartItems);
        when(cartDal.findById(1)).thenReturn(Optional.of(shoppingCartEntity));
        GetShoppingCartItemsRequest request = new GetShoppingCartItemsRequest(1);
        // Act
        GetShoppingCartItemsResponse response = shoppingCartService.getCart(request);
        // Assert
        assertNotNull(response);
        Map<Integer, ShoppingCartItem> itemsMap = response.getCartItems();
        assertNotNull(itemsMap);
        assertEquals(1, itemsMap.size());
    }
    @Test
    public void getCartShouldThrowException_whenCartWithId1DoesNotExist(){
        // Arrange 
        int cartId = 1;
        when(cartDal.findById(1)).thenReturn(Optional.empty());
        GetShoppingCartItemsRequest request =
                new GetShoppingCartItemsRequest(cartId);
        // Act 
        Throwable thrown  = assertThrows(CartNotFoundException.class,
                () -> shoppingCartService.getCart(request));
        // Assert
        assertEquals("404 NOT_FOUND \"CART COULD NOT BE FOUND\"", thrown.getMessage());
    }
    @Test
    public void createCartShouldWork_whenCartIdCreated(){
        // Arrange 
        UserEntity user = UserEntity.builder().id(1).build();
        ShoppingCartEntity entityToCreate =
                ShoppingCartEntity.builder().user(user).build();
        ShoppingCartEntity createdEntity =
                ShoppingCartEntity.builder().id(1).user(user).build();
        when(cartDal.save(entityToCreate)).thenReturn(createdEntity);
        
        CreateCartRequest request = new CreateCartRequest(user);
        // Act 
        CreateCartResponse response = shoppingCartService.createCart(request);
        // Assert 
        verify(cartDal).save(entityToCreate);
        assertEquals(createdEntity.getId(), response.getCartId());
    }
    @Test
    public void emptyCartShouldThrowException_WhenCartDoesNotExist(){
        // Arrange 
        Integer nonExistentId = 1;
        EmptyCartRequest request = new EmptyCartRequest(nonExistentId);
        // Act 
        Throwable thrown = assertThrows(CartNotFoundException.class,
                () -> shoppingCartService.emptyCart(request));
        // Assert 
        assertNotNull(thrown);
        assertEquals("404 NOT_FOUND \"CART COULD NOT BE FOUND\"", thrown.getMessage());
    }
    @Test
    public void emptyCartShouldEmpty_WhenCartExists(){
        // Arrange 
        UserEntity user = UserEntity.builder().id(1).build();
        ShoppingCartEntity createdEntity =
                ShoppingCartEntity.builder().id(1).user(user).build();
        EmptyCartRequest request = new EmptyCartRequest(createdEntity.getId());
        when(cartDal.findById(request.getCartId())).thenReturn(Optional.of(createdEntity));
        // Act 
        EmptyCartResponse response = shoppingCartService.emptyCart(request);
        // Assert 
        assertNotNull(response);
        assertEquals(createdEntity.getId(), response.getCartId());
    }
    @Test
    public void getCartOfUserShouldRetrieve_WhenUserExists(){
        // Arrange 
        UserEntity user =
                UserEntity.builder().id(1).userRole(UserRoleEntity.builder().id(1).role(RoleEnum.CLIENT).build()).build();
        ShoppingCartEntity createdEntity =
                ShoppingCartEntity.builder().id(1).user(user).cartItems(new HashMap<>()).build();
        ShoppingCart expectedCart =
                ShoppingCart.builder().id(1).user(User.builder().id(1).userRole(UserRole.builder().id(1).role(Role.CLIENT).build()).build()).cartItems(new HashMap<>()).build();
        GetShoppingCartRequest request =
                new GetShoppingCartRequest(user.getId());
        when(cartDal.getShoppingCartBasedOnUserId(user.getId())).thenReturn(Optional.of(createdEntity));
        // Act 
        GetShoppingCartResponse response =
                shoppingCartService.getCartBasedOnUserId(request);
        // Assert 
        assertNotNull(response);
        assertEquals(expectedCart, response.getShoppingCart());
    }
    @Test
    public void getCartOfUserShouldThrowException_WhenUserDoesNotExist(){
        // Arrange 
        UserEntity user = UserEntity.builder().id(1).build();
        GetShoppingCartRequest request =
                new GetShoppingCartRequest(user.getId());
        when(cartDal.getShoppingCartBasedOnUserId(user.getId())).thenReturn(Optional.empty());
        // Act 
        Throwable thrown = assertThrows(CartNotFoundException.class,
                () -> shoppingCartService.getCartBasedOnUserId(request));
        // Assert 
        assertNotNull(thrown);
        assertEquals("404 NOT_FOUND \"CART COULD NOT BE FOUND\"", thrown.getMessage());
    }
}
