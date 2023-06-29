package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.IShoppingCartDal;
import fontys.s3.andreipieleanu.datalayer.IShoppingCartItemDal;
import fontys.s3.andreipieleanu.datalayer.entities.*;
import fontys.s3.andreipieleanu.domain.requestresponse.item.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TestCartItemService {
    private final IShoppingCartItemDal cartItemDal = mock(IShoppingCartItemDal.class);
    private CartItemService cartItemService;
    private ShoppingCartEntity cart;
    @BeforeEach
    public void setUp() {
        IShoppingCartDal shoppingCartDal = mock(IShoppingCartDal.class);
        cartItemService = new CartItemService(cartItemDal, shoppingCartDal);
        UserEntity user = UserEntity.builder().id(1).build();
        cart = ShoppingCartEntity.builder().id(1).user(user).cartItems(new HashMap<>()).build();
        when(shoppingCartDal.findById(1)).thenReturn(Optional.of(cart));
    }

    @Test
    public void addItemToCartShouldAdd_WhenNoSuchItemExists() {
        CartItemEntity itemToAdd = CartItemEntity
                .builder()
                .item(ClothesEntity.builder().id(1).build())
                .amount(2)
                .shoppingCartEntity(cart)
                .build();
        EditItemRequest request = new EditItemRequest(1, 1, 2);
        
        EditItemResponse response = cartItemService.editItem(request);
        
        verify(cartItemDal).save(itemToAdd);
        assertNotNull(response);
        assertEquals(1, response.getUpdatedId());
    }
    @Test
    public void editItemShouldChangeAmount_WhenItemAlreadyExists(){
        CartItemEntity itemToAdd = CartItemEntity
                .builder()
                .item(ClothesEntity.builder().id(1).build())
                .amount(2)
                .build();
        Map<Integer, CartItemEntity> itemsInCart = new HashMap<>();
        itemsInCart.put(1, itemToAdd);
        cart.setCartItems(itemsInCart);
        EditItemRequest request = new EditItemRequest(1, 1, 2);

        EditItemResponse response = cartItemService.editItem(request);
        
        verify(cartItemDal).editItem(itemToAdd);
        assertNotNull(response);
        assertEquals(4, cart.getCartItems().get(1).getAmount());
    }
    @Test
    public void deleteItemFromCartShouldWork_WhenItemIsInCart(){
        CartItemEntity itemToAdd = CartItemEntity
                .builder()
                .item(ClothesEntity.builder().id(1).build())
                .amount(2)
                .build();
        Map<Integer, CartItemEntity> itemsInCart = new HashMap<>();
        itemsInCart.put(1, itemToAdd);
        cart.setCartItems(itemsInCart);
        RemoveItemFromCartRequest request = new RemoveItemFromCartRequest(1, 1);
        
        RemoveItemFromCartResponse response = cartItemService.removeItem(request);
        
        verify(cartItemDal).removeItem(request.getCartId(), request.getCartId());
        assertNotNull(response);
        assertEquals("Item removed successfully!", response.getMessage());
    }
    @Test
    public void deleteItemShouldNotWork_WhenNoItemHasBeenAdded(){
        RemoveItemFromCartRequest request = new RemoveItemFromCartRequest(1, 1);

        RemoveItemFromCartResponse response = cartItemService.removeItem(request);

        assertNotNull(response);
        assertEquals("Item could not be removed because cloth id does not exist", response.getMessage());
    }
}
