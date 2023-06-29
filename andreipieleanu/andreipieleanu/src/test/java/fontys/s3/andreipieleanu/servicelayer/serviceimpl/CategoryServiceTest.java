package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.ICategoryDal;
import fontys.s3.andreipieleanu.datalayer.entities.CategoryEntity;
import fontys.s3.andreipieleanu.domain.Category;
import fontys.s3.andreipieleanu.domain.requestresponse.category.GetSubcategoriesResponse;
import fontys.s3.andreipieleanu.servicelayer.ICategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    private ICategoryDal categoryDal;
    private ICategoryService categoryService;
    @BeforeEach
    public void setUp(){
        categoryDal = mock(ICategoryDal.class);
        categoryService = new CategoryService(categoryDal);
    }
    Map<Integer, CategoryEntity> categoriesToAdd = Map.of(
            1,
            CategoryEntity.builder().id(1).name("category").parentCategory(null).build(),
            2,
            CategoryEntity.builder().id(2).name("subcategory 1").parentCategory(CategoryEntity.builder().id(1).build()).build(),
            3,
            CategoryEntity.builder().id(3).name("subcategory 2").parentCategory(CategoryEntity.builder().id(1).build()).build()
    );
    Map<Integer, Category> expectedSubcategories = Map.of(
            2,
            Category.builder().id(2).name("subcategory 1").build(),
            3,
            Category.builder().id(3).name("subcategory 2").build()
    );
    @Test
    public void getAllSubcategoriesShouldRetrieveThem_WhenSubcategoriesExist(){
        // Arrange 
        when(categoryDal.getAllSubcategories()).thenReturn(categoriesToAdd.values().stream().filter(categoryEntity -> categoryEntity.getParentCategory() != null).collect(Collectors.toList()));
        // Act 
        GetSubcategoriesResponse response =
                categoryService.getAllSubcategories();
        // Assert 
        assertNotNull(response);
        assertEquals(expectedSubcategories, response.getCategories());
    }
}