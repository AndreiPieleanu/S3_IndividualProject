package fontys.s3.andreipieleanu.controller;

import fontys.s3.andreipieleanu.domain.Category;
import fontys.s3.andreipieleanu.domain.Clothes;
import fontys.s3.andreipieleanu.domain.Sizes;
import fontys.s3.andreipieleanu.domain.requestresponse.clothes.GetClothesRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.clothes.GetClothesResponse;
import fontys.s3.andreipieleanu.servicelayer.serviceimpl.ClothesService;
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
public class ClothesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClothesService clothesService;
    
    @Test
    public void getAllClothesShouldReturnStatus200_WhenClothesExist() throws Exception {
        // Arrange
        GetClothesRequest request = GetClothesRequest.builder().build();
        when(clothesService.getAllClothes(request, true)).thenReturn(
                new GetClothesResponse(Map.of(
                        1,
                        Clothes.builder().id(1)
                        .name("New Shirt Name")
                        .description("A nice shirt")
                        .price(19.99)
                        .amountInStock(10)
                        .isActive(true)
                        .size(Sizes.S)
                        .measAmount(1)
                        .measUnit("item")
                        .subcategory(Category.builder().id(1).build()).build(),
                        2,
                        Clothes.builder().id(1)
                                .name("New Shirt Name 2")
                                .description("A nice shirt 2")
                                .price(19.99)
                                .amountInStock(10)
                                .isActive(true)
                                .size(Sizes.S)
                                .measAmount(1)
                                .measUnit("item")
                                .subcategory(Category.builder().id(1).build()).build()
                ))
        );
        // Act 
        mockMvc.perform(get("/clothes?isActive=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                    {
                        "foundClothes":{"1":{"id":1,"name":"New Shirt Name","description":"A nice shirt","price":19.99,"amountInStock":10,"isActive":true,"size":"S","measAmount":1,"measUnit":"item","subcategory":{"id":1,"name":null,"parentCategory":null}},"2":{"id":1,"name":"New Shirt Name 2","description":"A nice shirt 2","price":19.99,"amountInStock":10,"isActive":true,"size":"S","measAmount":1,"measUnit":"item","subcategory":{"id":1,"name":null,"parentCategory":null}}}
                    }
                    """));
        // Assert
        verify(clothesService).getAllClothes(request, true);
    }
}