package fontys.s3.andreipieleanu.controller;

import fontys.s3.andreipieleanu.domain.requestresponse.category.GetSubcategoriesResponse;
import fontys.s3.andreipieleanu.servicelayer.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;
    @GetMapping
    public ResponseEntity<GetSubcategoriesResponse> getAllSubcategories(){
        return ResponseEntity.ok(categoryService.getAllSubcategories());
    }
}
