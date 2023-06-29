package fontys.s3.andreipieleanu.controller;

import fontys.s3.andreipieleanu.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.andreipieleanu.domain.requestresponse.address.*;
import fontys.s3.andreipieleanu.servicelayer.IAddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/addresses")
@AllArgsConstructor
public class AddressController {
    private final IAddressService addressService;
    @IsAuthenticated
    @RolesAllowed({"ROLE_CLIENT"})
    @GetMapping("{userId}")
    public ResponseEntity<GetAddressResponse> getAllAddressesOfUserWithId(@PathVariable("userId")int id){
        GetAddressResponse foundAddress =
                addressService.getAllAddressesOfUserWithId(new GetAddressRequest(id));
        return ResponseEntity.ok(foundAddress);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_CLIENT"})
    @PutMapping("{addressId}")
    public ResponseEntity<UpdateAddressResponse> updateAddress(@PathVariable(
            "addressId") int id, @RequestBody @Valid UpdateAddressRequest request){
        request.setId(id);
        UpdateAddressResponse response = addressService.updateAddress(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_CLIENT"})
    @PostMapping
    public ResponseEntity<CreateAddressResponse> createAddress(@RequestBody @Valid CreateAddressRequest request){
        CreateAddressResponse response = addressService.createAddress(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_CLIENT"})
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddressWitId(@PathVariable("addressId") Integer id){
        addressService.deleteAddressWithId(new RemoveAddressRequest(id));
        return ResponseEntity.noContent().build();
    }
}
