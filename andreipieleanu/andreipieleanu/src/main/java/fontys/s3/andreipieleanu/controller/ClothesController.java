package fontys.s3.andreipieleanu.controller;

import fontys.s3.andreipieleanu.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.andreipieleanu.domain.requestresponse.clothes.*;
import fontys.s3.andreipieleanu.servicelayer.IClothesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/clothes")
@AllArgsConstructor
public class ClothesController {
    private final IClothesService clothesService;
    @GetMapping
    public ResponseEntity<GetClothesResponse> getAllActiveClothes(
            @RequestParam(value = "min", required = false) Optional<Double> min,
            @RequestParam(value = "max", required = false) Optional<Double> max,
            @RequestParam(value = "subcategoryId", required = false) Optional<Integer> subcategoryId,
            @RequestParam(value = "size", required = false) Optional<String> size,
            @RequestParam(value = "isActive") boolean isActive) {
        GetClothesRequest request =
                GetClothesRequest.builder().min(min).max(max).subcategoryId(subcategoryId).size(size).build();
        return ResponseEntity.ok(clothesService.getAllClothes(request, isActive));
    }
    @GetMapping("{clothesId}")
    public ResponseEntity<FindOneClothResponse> getClothesById(@PathVariable(
            "clothesId")int id){
        return ResponseEntity.status(HttpStatus.FOUND).body(clothesService.getClothesById(new FindOneClothRequest(id)));
    }
    @PutMapping("{clothesId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_WORKER"})
    public ResponseEntity<UpdateClothesResponse> updateClothes(@PathVariable("clothesId") int id,
                                                               @RequestBody @Valid UpdateClothesRequest request){
        request.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(clothesService.updateClothes(request));
    }
    @PostMapping
    @IsAuthenticated
    @RolesAllowed({"ROLE_WORKER"})
    public ResponseEntity<CreateClothesResponse> addClothes(@RequestBody @Valid CreateClothesRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(clothesService.createClothes(request));
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_WORKER"})
    @DeleteMapping("{clothesId}")
    public ResponseEntity<Void> deleteClothes(@PathVariable("clothesId") int id){
        clothesService.deleteClothesById(new FindOneClothRequest(id));
        //clothesService.
        return ResponseEntity.noContent().build();
    }
}
