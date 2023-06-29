package fontys.s3.andreipieleanu.servicelayer;

import fontys.s3.andreipieleanu.domain.requestresponse.cart.*;


public interface IShoppingCartService {
    CreateCartResponse createCart(CreateCartRequest request);
    EmptyCartResponse emptyCart(EmptyCartRequest request);
    GetShoppingCartItemsResponse getCart(GetShoppingCartItemsRequest request);
    GetShoppingCartResponse getCartBasedOnUserId(GetShoppingCartRequest request);
}
