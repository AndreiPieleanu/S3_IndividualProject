package fontys.s3.andreipieleanu.servicelayer.converters;

import fontys.s3.andreipieleanu.datalayer.entities.CartItemEntity;
import fontys.s3.andreipieleanu.datalayer.entities.ShoppingCartEntity;
import fontys.s3.andreipieleanu.domain.ShoppingCart;
import fontys.s3.andreipieleanu.domain.ShoppingCartItem;

import java.util.HashMap;
import java.util.Map;

public final class ShoppingCartConverter {
    private ShoppingCartConverter(){}
    public static ShoppingCartEntity convert(ShoppingCart cart){
        Map<Integer, CartItemEntity> convertedItems = new HashMap<>();
        cart.getCartItems().forEach((k, v) -> convertedItems.put(k,
                ShoppingCartItemConverter.convert(v)));
        return ShoppingCartEntity
                .builder()
                .id(cart.getId())
                .user(UserConverter.convert(cart.getUser()))
                .cartItems(convertedItems)
                .build();
    }
    public static ShoppingCart convert(ShoppingCartEntity cartEntity){
        Map<Integer, ShoppingCartItem> convertedItems = new HashMap<>();
        cartEntity.getCartItems().forEach((k, v) -> convertedItems.put(k,
                ShoppingCartItemConverter.convert(v)));
        return ShoppingCart
                .builder()
                .id(cartEntity.getId())
                .user(UserConverter.convert(cartEntity.getUser()))
                .cartItems(convertedItems)
                .build();
    }
}
