package fontys.s3.andreipieleanu.controller;

import fontys.s3.andreipieleanu.domain.Category;
import fontys.s3.andreipieleanu.domain.requestresponse.category.GetSubcategoriesResponse;
import fontys.s3.andreipieleanu.servicelayer.serviceimpl.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;
    
    @Test
    public void getAllCategoriesShouldReturnStatus200_WhenCategoriesExist() throws Exception {
        // Arrange 
        when(categoryService.getAllSubcategories()).thenReturn(
                new GetSubcategoriesResponse(Map.of(
                        1,
                        Category.builder().id(1).name("Categ1").parentCategory(null).build(),
                        2,
                        Category.builder().id(1).name("Categ2").parentCategory(null).build()
                ))
        );
        // Act 
        mockMvc.perform(get("/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                    {
                        "categories":{"1":{"id":1,"name":"Categ1","parentCategory":null},"2":{"id":1,"name":"Categ2","parentCategory":null}}
                    }
                    """));
        // Assert
        verify(categoryService).getAllSubcategories();
    }
}