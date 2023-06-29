package fontys.s3.andreipieleanu.servicelayer.converters;

import fontys.s3.andreipieleanu.datalayer.entities.CartItemEntity;
import fontys.s3.andreipieleanu.domain.ShoppingCartItem;

public final class ShoppingCartItemConverter {
    private ShoppingCartItemConverter(){}
    public static ShoppingCartItem convert(CartItemEntity entity){
        return ShoppingCartItem
                .builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .item(ClothesConverter.convert(entity.getItem()))
                .build();
    }
    public static CartItemEntity convert(ShoppingCartItem cartItem){
        return CartItemEntity
                .builder()
                .id(cartItem.getId())
                .amount(cartItem.getAmount())
                .item(ClothesConverter.convert(cartItem.getItem()))
                .build();
    }
}
