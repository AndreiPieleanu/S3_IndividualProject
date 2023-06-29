package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.IShoppingCartDal;
import fontys.s3.andreipieleanu.datalayer.IShoppingCartItemDal;
import fontys.s3.andreipieleanu.datalayer.entities.CartItemEntity;
import fontys.s3.andreipieleanu.datalayer.entities.ClothesEntity;
import fontys.s3.andreipieleanu.datalayer.entities.ShoppingCartEntity;
import fontys.s3.andreipieleanu.domain.requestresponse.item.EditItemRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.item.EditItemResponse;
import fontys.s3.andreipieleanu.domain.requestresponse.item.RemoveItemFromCartRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.item.RemoveItemFromCartResponse;
import fontys.s3.andreipieleanu.servicelayer.ICartItemService;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.CartNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemService implements ICartItemService {
    private final IShoppingCartItemDal cartItemDal;
    private final IShoppingCartDal shoppingCartDal;
    /**
     * <p>Add item to the cart. If the item does not exist, it will create 
     * it. Otherwise, it will edit its amount</p>
     * @return EditItemResponse object containing the if of the added/edited 
     * @throws CartNotFoundException if no cart with that id could be found
     */
    @Override
    @Transactional
    public EditItemResponse editItem(EditItemRequest request) {
        ShoppingCartEntity foundCart =
                shoppingCartDal.findById(request.getCartId()).orElseThrow(CartNotFoundException::new);
        Map<Integer, CartItemEntity> foundItems = foundCart.getCartItems();
        Optional<CartItemEntity> optionalCartItemEntity = foundItems.values().stream()
                .filter(cartItemEntity -> cartItemEntity.getItem().getId().equals(request.getClothesId()))
                .findFirst();
        if(optionalCartItemEntity.isPresent()){
            CartItemEntity foundExistingItem = optionalCartItemEntity.get();
            foundExistingItem.setAmount(
                    foundExistingItem.getAmount() + request.getAmountToAdd()
            );
            cartItemDal.editItem(foundExistingItem);
        }
        else{
            cartItemDal.save(
                    CartItemEntity
                            .builder()
                            .shoppingCartEntity(foundCart)
                            .item(ClothesEntity.builder().id(request.getClothesId()).build())
                            .amount(request.getAmountToAdd())
                            .build()
            );
        }
        return new EditItemResponse(request.getClothesId());
    }

    @Override
    @Transactional
    public RemoveItemFromCartResponse removeItem(RemoveItemFromCartRequest request) {
        ShoppingCartEntity foundCart =
                shoppingCartDal.findById(request.getCartId()).orElseThrow(CartNotFoundException::new);
        Map<Integer, CartItemEntity> foundItems = foundCart.getCartItems();
        Optional<CartItemEntity> optionalCartItemEntity = foundItems.values().stream()
                .filter(cartItemEntity -> cartItemEntity.getItem().getId().equals(request.getClothesId()))
                .findFirst();
        if(optionalCartItemEntity.isEmpty()){
            return new RemoveItemFromCartResponse("Item could not be removed " +
                    "because cloth id does not exist");
        }
        cartItemDal.removeItem(request.getClothesId(), request.getCartId());
        return new RemoveItemFromCartResponse("Item removed successfully!");
    }
}
