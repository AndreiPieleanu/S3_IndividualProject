package fontys.s3.andreipieleanu.controller;

import fontys.s3.andreipieleanu.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.andreipieleanu.domain.requestresponse.item.EditItemRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.item.EditItemResponse;
import fontys.s3.andreipieleanu.domain.requestresponse.item.RemoveItemFromCartRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.item.RemoveItemFromCartResponse;
import fontys.s3.andreipieleanu.servicelayer.ICartItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
@RestController
@RequestMapping("/cartItem")
@AllArgsConstructor
public class CartItemController {
    private final ICartItemService cartItemService;
    @IsAuthenticated
    @RolesAllowed({"ROLE_CLIENT"})
    @PostMapping
    public ResponseEntity<EditItemResponse> changeItemFromCart(@RequestBody @Valid EditItemRequest request){
        return ResponseEntity.ok(cartItemService.editItem(request));
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_CLIENT"})
    @DeleteMapping
    public ResponseEntity<RemoveItemFromCartResponse> removeItemFromCart(@RequestBody @Valid RemoveItemFromCartRequest request){
        return ResponseEntity.ok(cartItemService.removeItem(request));
    }
}
