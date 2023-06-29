package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.IShoppingCartDal;
import fontys.s3.andreipieleanu.datalayer.entities.*;
import fontys.s3.andreipieleanu.domain.ShoppingCart;
import fontys.s3.andreipieleanu.domain.ShoppingCartItem;
import fontys.s3.andreipieleanu.domain.requestresponse.cart.*;
import fontys.s3.andreipieleanu.servicelayer.IShoppingCartService;
import fontys.s3.andreipieleanu.servicelayer.converters.ShoppingCartConverter;
import fontys.s3.andreipieleanu.servicelayer.converters.ShoppingCartItemConverter;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.CartNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class ShoppingCartService implements IShoppingCartService {
    private final IShoppingCartDal cartDal;

    @Override
    public CreateCartResponse createCart(CreateCartRequest request) {
        ShoppingCartEntity entityToCreate = ShoppingCartEntity
                .builder()
                .user(request.getUser())
                .build();
        ShoppingCartEntity createdEntity = cartDal.save(entityToCreate);
        return new CreateCartResponse(createdEntity.getId());
    }

    @Override
    public EmptyCartResponse emptyCart(EmptyCartRequest request) {
        if(cartDal.findById(request.getCartId()).isEmpty()) {
            throw new CartNotFoundException();
        }
        cartDal.emptyCartWithId(request.getCartId());
        return new EmptyCartResponse(request.getCartId());
    }

    @Override
    @Transactional
    public GetShoppingCartItemsResponse getCart(GetShoppingCartItemsRequest request) {
        ShoppingCartEntity foundCart =
                cartDal.findById(request.getCartId()).orElseThrow(CartNotFoundException::new);
        Map<Integer, CartItemEntity> foundItems = foundCart.getCartItems();
        Map<Integer, ShoppingCartItem> convertedItems = new HashMap<>();
        foundItems.forEach((k, v) -> convertedItems.put(k, ShoppingCartItemConverter.convert(v)));
        return new GetShoppingCartItemsResponse(convertedItems);
    }

    @Override
    public GetShoppingCartResponse getCartBasedOnUserId(GetShoppingCartRequest request) {
        ShoppingCartEntity foundEntity =
                cartDal.getShoppingCartBasedOnUserId(request.getUserId()).orElseThrow(CartNotFoundException::new);
        ShoppingCart convertedCart = ShoppingCartConverter.convert(foundEntity);
        return new GetShoppingCartResponse(convertedCart);
    }
    
}
