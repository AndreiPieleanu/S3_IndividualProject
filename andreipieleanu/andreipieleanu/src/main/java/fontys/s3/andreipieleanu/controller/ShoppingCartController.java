package fontys.s3.andreipieleanu.controller;

import fontys.s3.andreipieleanu.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.andreipieleanu.domain.requestresponse.cart.*;
import fontys.s3.andreipieleanu.servicelayer.IShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class ShoppingCartController {
    private final IShoppingCartService shoppingCartService;
    @GetMapping("{userId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_CLIENT"})
    public ResponseEntity<GetShoppingCartResponse> getAllCartItemsOfCartWithId(@PathVariable(
            "userId")int userId){
        GetShoppingCartRequest request =
                new GetShoppingCartRequest(userId);
        return ResponseEntity.ok(shoppingCartService.getCartBasedOnUserId(request));
    }
    @PutMapping("{cartId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_CLIENT"})
    public ResponseEntity<EmptyCartResponse> emptyCart(@PathVariable("cartId") Integer cartId){
        return ResponseEntity.ok(shoppingCartService.emptyCart(new EmptyCartRequest(cartId)));
    }
}
