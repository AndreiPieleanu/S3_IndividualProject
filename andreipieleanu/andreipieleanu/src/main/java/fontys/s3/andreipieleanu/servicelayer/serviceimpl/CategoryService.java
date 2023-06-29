package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.ICategoryDal;
import fontys.s3.andreipieleanu.domain.Category;
import fontys.s3.andreipieleanu.domain.requestresponse.category.GetSubcategoriesResponse;
import fontys.s3.andreipieleanu.servicelayer.ICategoryService;
import fontys.s3.andreipieleanu.servicelayer.converters.CategoryConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {
    private ICategoryDal categoryDal;
    @Override
    public GetSubcategoriesResponse getAllSubcategories() {
        Map<Integer, Category> categories = new HashMap<>();
        categoryDal.getAllSubcategories().stream().map(CategoryConverter::convert
        ).forEach(category -> categories.put(category.getId(), category));
        return new GetSubcategoriesResponse(categories);
    }
}
