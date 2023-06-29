package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.IClothesDal;
import fontys.s3.andreipieleanu.datalayer.entities.CategoryEntity;
import fontys.s3.andreipieleanu.datalayer.entities.ClothesEntity;
import fontys.s3.andreipieleanu.datalayer.entities.SizesEntity;
import fontys.s3.andreipieleanu.domain.Category;
import fontys.s3.andreipieleanu.domain.Clothes;
import fontys.s3.andreipieleanu.domain.Sizes;
import fontys.s3.andreipieleanu.domain.requestresponse.clothes.*;
import fontys.s3.andreipieleanu.servicelayer.IClothesService;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.ClothesNotFoundException;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.DuplicatedClothesException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TestClothesService {
    IClothesDal clothesDal = mock(IClothesDal.class);
    IClothesService clothesService = new ClothesService(clothesDal);
    @Test
    public void addClotheShouldReturnResponse_WhenClothesIsNew(){
        // Arrange 
        ClothesEntity clothesToAdd = ClothesEntity
                .builder()
                .name("new t-shirt")
                .description("new desc")
                .price(12.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(CategoryEntity.builder().id(1).build())
                .build();
        ClothesEntity savedEntity = ClothesEntity.builder().id(1).build();
        CreateClothesRequest request = new CreateClothesRequest(
                clothesToAdd.getName(),
                clothesToAdd.getDescription(),
                clothesToAdd.getPrice(),
                clothesToAdd.getAmountInStock(),
                clothesToAdd.isActive(),
                clothesToAdd.getSizesEntity().getValue(),
                clothesToAdd.getMeasAmount(),
                clothesToAdd.getMeasUnit(),
                clothesToAdd.getSubcategoryEntity().getId()
        );
        when(clothesDal.save(any(ClothesEntity.class))).thenReturn(savedEntity);
        CreateClothesResponse response = clothesService.createClothes(request);
        verify(clothesDal).save(clothesToAdd);
        assertNotNull(response);
        assertEquals(1, response.getClothesId());
    }
    @Test
    public void addClothesShouldThrowException_WhenExistingClothesIsAdded(){
        // Arrange 
        CreateClothesRequest request = new CreateClothesRequest();
        request.setName("Shirt");
        request.setDescription("A white shirt");
        request.setPrice(20.0);
        request.setAmountInStock(10);
        request.setActive(true);
        request.setSize(1);
        request.setMeasAmount(1);
        request.setMeasUnit("unit");
        request.setSubcategoryId(1);

        ClothesEntity existingClothes = ClothesEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .amountInStock(request.getAmountInStock())
                .isActive(request.isActive())
                .sizesEntity(SizesEntity.getSize(request.getSize()))
                .measAmount(request.getMeasAmount())
                .measUnit(request.getMeasUnit())
                .subcategoryEntity(CategoryEntity.builder().id(request.getSubcategoryId()).build())
                .build();
        when(clothesDal.findAll()).thenReturn(List.of(existingClothes));
        Throwable thrown = assertThrows(DuplicatedClothesException.class, () -> clothesService.createClothes(request));
        assertEquals("400 BAD_REQUEST \"CLOTHES IS ALREADY ADDED\"", thrown.getMessage());
    }
    @Test
    public void updateProductShouldWork_WhenProductIsPresent() {
        // Arrange
        ClothesEntity updatedClothesEntity = new ClothesEntity();
        updatedClothesEntity.setId(1);
        updatedClothesEntity.setName("New Name");
        updatedClothesEntity.setPrice(20.0);
        updatedClothesEntity.setAmountInStock(5);
        updatedClothesEntity.setDescription("A description");
        updatedClothesEntity.setActive(true);
        updatedClothesEntity.setSizesEntity(SizesEntity.S);
        updatedClothesEntity.setMeasAmount(100);
        updatedClothesEntity.setMeasUnit("g");
        updatedClothesEntity.setSubcategoryEntity(CategoryEntity.builder().id(1).build());

        when(clothesDal.existsById(1)).thenReturn(true);
        when(clothesDal.updateClothes(any(ClothesEntity.class))).thenReturn(1);

        // Act
        UpdateClothesRequest updateRequest = new UpdateClothesRequest();
        updateRequest.setId(1);
        updateRequest.setName("New Name");
        updateRequest.setPrice(20.0);
        updateRequest.setAmountInStock(5);
        updateRequest.setDescription("A description");
        updateRequest.setActive(true);
        updateRequest.setSize(1);
        updateRequest.setMeasAmount(100);
        updateRequest.setMeasUnit("g");
        updateRequest.setSubcategoryId(1);
        UpdateClothesResponse updateResponse = clothesService.updateClothes(updateRequest);

        // Assert
        assertNotNull(updateResponse);
        assertEquals(updatedClothesEntity.getId(), updateResponse.getClothesId());
    }
    @Test
    public void updateProductShouldThrowException_WhenProductIsNotPresent() {
        // arrange
        ClothesEntity updatedClothes = ClothesEntity.builder()
                .id(1)
                .name("New Shirt Name")
                .description("A nice shirt")
                .price(19.99)
                .amountInStock(10)
                .isActive(true)
                .sizesEntity(SizesEntity.S)
                .measAmount(1)
                .measUnit("item")
                .subcategoryEntity(CategoryEntity.builder().id(1).build())
                .build();

        when(clothesDal.existsById(1)).thenReturn(false);

        // act
        UpdateClothesRequest request = new UpdateClothesRequest(1, "New Shirt Name", "A nice shirt",
                19.99, 10, true, 1, 1, "item", 1);
        
        Throwable thrown = assertThrows(ClothesNotFoundException.class, () -> clothesService.updateClothes(request));
        // assert
        verify(clothesDal).existsById(1);
        verify(clothesDal, never()).updateClothes(updatedClothes);
        assertEquals("404 NOT_FOUND \"CLOTHES COULD NOT BE FOUND\"", thrown.getMessage());
    }
    @Test
    public void deleteProductShouldWork_WhenProductIsPresent() {
        // arrange
        doNothing().when(clothesDal).deleteById(1);

        // act
        FindOneClothRequest request = new FindOneClothRequest(1);
        clothesService.deleteClothesById(request);

        // assert
        verify(clothesDal).deleteById(1);
    }
    @Test
    public void getProductShouldWork_WhenProductIsPresent() {
        // Create a list of clothes items
        ClothesEntity clothesToAdd = ClothesEntity
                .builder()
                .name("new t-shirt")
                .description("new desc")
                .price(12.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(CategoryEntity.builder().id(1).build())
                .build();
        ClothesEntity savedEntity = ClothesEntity
                .builder()
                .id(1)
                .name("new t-shirt")
                .description("new desc")
                .price(12.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(CategoryEntity.builder().id(1).build())
                .build();
        CreateClothesRequest request = new CreateClothesRequest(
                clothesToAdd.getName(),
                clothesToAdd.getDescription(),
                clothesToAdd.getPrice(),
                clothesToAdd.getAmountInStock(),
                clothesToAdd.isActive(),
                clothesToAdd.getSizesEntity().getValue(),
                clothesToAdd.getMeasAmount(),
                clothesToAdd.getMeasUnit(),
                clothesToAdd.getSubcategoryEntity().getId()
        );
        List<ClothesEntity> clothesList = new ArrayList<>();
        clothesList.add(clothesToAdd);
        when(clothesDal.save(any(ClothesEntity.class))).thenReturn(savedEntity);
        when(clothesDal.findById(1)).thenReturn(Optional.ofNullable(savedEntity));
        clothesService.createClothes(request);

        // Call the method to get a clothes item from the list
        FindOneClothRequest findOneClothRequest = new FindOneClothRequest(1);
        FindOneClothResponse response = clothesService.getClothesById(findOneClothRequest);

        // Verify that the method returned a clothes item from the list
        assertNotNull(response);
        assertNotNull(response.getFoundClothes());
        assertEquals(clothesList.get(0), savedEntity);
    }
    @Test
    public void getAllActiveClothesShouldReturnActiveClothes_WhenActiveClothesAreAdded(){
        // Arrange 
        CategoryEntity subCategory = CategoryEntity.builder().id(1).build();
        ClothesEntity addedClothes1 = ClothesEntity
                .builder()
                .id(1)
                .name("new t-shirt")
                .description("new desc")
                .price(12.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(subCategory)
                .build();
        ClothesEntity addedClothes2 = ClothesEntity
                .builder()
                .id(2)
                .name("new t-shirt")
                .description("new desc")
                .price(12.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(subCategory)
                .build();
        when(clothesDal.findAll()).thenReturn(List.of(addedClothes1,
                addedClothes2));
        Integer expectedClothes = 2;
        GetClothesRequest noFilters = GetClothesRequest.builder().build();
        // Act 
        GetClothesResponse response = clothesService.getAllClothes(noFilters,
                true);
        // Assert 
        assertNotNull(response);
        assertEquals(expectedClothes, response.getFoundClothes().size());
    }
    @Test
    public void getAllInactiveClothesShouldReturnOneInactiveClothes_WhenOneInactiveClothesAreAdded(){
        // Arrange 
        CategoryEntity subCategory = CategoryEntity.builder().id(1).build();
        ClothesEntity addedClothes1 = ClothesEntity
                .builder()
                .id(1)
                .name("new t-shirt")
                .description("new desc")
                .price(12.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(false)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(subCategory)
                .build();
        ClothesEntity addedClothes2 = ClothesEntity
                .builder()
                .id(2)
                .name("new t-shirt")
                .description("new desc")
                .price(12.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(subCategory)
                .build();
        when(clothesDal.findAll()).thenReturn(List.of(addedClothes1,
                addedClothes2));
        Integer expectedClothes = 1;
        GetClothesRequest noFilters = GetClothesRequest.builder().build();
        // Act 
        GetClothesResponse response = clothesService.getAllClothes(noFilters,
                false);
        // Assert 
        assertNotNull(response);
        assertEquals(expectedClothes, response.getFoundClothes().size());
    }
    @Test
    public void getAllClothesWithMinPrice10ShouldReturn1_When1SuchItemExists(){
        // Arrange 
        CategoryEntity subCategory = CategoryEntity.builder().id(1).build();
        ClothesEntity addedClothes1 = ClothesEntity
                .builder()
                .id(1)
                .name("new t-shirt")
                .description("new desc")
                .price(5.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(subCategory)
                .build();
        ClothesEntity addedClothes2 = ClothesEntity
                .builder()
                .id(2)
                .name("new purse")
                .description("new purse desc")
                .price(25.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(subCategory)
                .build();
        Map<Integer, Clothes> expectedClothes = Map.of(
                2, 
                Clothes
                        .builder()
                        .id(2)
                        .name("new purse")
                        .description("new purse desc")
                        .price(25.99)
                        .amountInStock(100)
                        .size(Sizes.S)
                        .isActive(true)
                        .measAmount(1)
                        .measUnit("piece")
                        .subcategory(Category.builder().id(1).build())
                        .build()
        );
        when(clothesDal.findAll()).thenReturn(List.of(addedClothes1,
                addedClothes2));
        GetClothesRequest request =
                GetClothesRequest.builder().min(Optional.of(10d)).build();
        // Act 
        GetClothesResponse response = clothesService.getAllClothes(request,
                true);
        // Assert 
        assertNotNull(response);
        assertEquals(expectedClothes, response.getFoundClothes());
    }
    @Test
    public void getAllClothesWithMaxPrice20ShouldReturn1_When1SuchItemExists(){
        // Arrange 
        CategoryEntity subCategory = CategoryEntity.builder().id(1).build();
        ClothesEntity addedClothes1 = ClothesEntity
                .builder()
                .id(1)
                .name("new t-shirt")
                .description("new desc")
                .price(5.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(subCategory)
                .build();
        ClothesEntity addedClothes2 = ClothesEntity
                .builder()
                .id(2)
                .name("new purse")
                .description("new purse desc")
                .price(25.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(subCategory)
                .build();
        Map<Integer, Clothes> expectedClothes = Map.of(
                1,
                Clothes
                        .builder()
                        .id(1)
                        .name("new t-shirt")
                        .description("new desc")
                        .price(5.99)
                        .amountInStock(100)
                        .size(Sizes.S)
                        .isActive(true)
                        .measAmount(1)
                        .measUnit("piece")
                        .subcategory(Category.builder().id(1).build())
                        .build()
        );
        when(clothesDal.findAll()).thenReturn(List.of(addedClothes1,
                addedClothes2));
        GetClothesRequest request =
                GetClothesRequest.builder().max(Optional.of(10d)).build();
        // Act 
        GetClothesResponse response = clothesService.getAllClothes(request,
                true);
        // Assert 
        assertNotNull(response);
        assertEquals(expectedClothes, response.getFoundClothes());
    }
    @Test
    public void getAllClothesWithSizeSShouldReturn2_When2SuchItemsExist(){
        // Arrange 
        CategoryEntity subCategory = CategoryEntity.builder().id(1).build();
        ClothesEntity addedClothes1 = ClothesEntity
                .builder()
                .id(1)
                .name("new t-shirt")
                .description("new desc")
                .price(5.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(subCategory)
                .build();
        ClothesEntity addedClothes2 = ClothesEntity
                .builder()
                .id(2)
                .name("new purse")
                .description("new purse desc")
                .price(25.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(subCategory)
                .build();
        Map<Integer, Clothes> expectedClothes = Map.of(
                1,
                Clothes
                        .builder()
                        .id(1)
                        .name("new t-shirt")
                        .description("new desc")
                        .price(5.99)
                        .amountInStock(100)
                        .size(Sizes.S)
                        .isActive(true)
                        .measAmount(1)
                        .measUnit("piece")
                        .subcategory(Category.builder().id(1).build())
                        .build(),
                2,
                Clothes
                        .builder()
                        .id(2)
                        .name("new purse")
                        .description("new purse desc")
                        .price(25.99)
                        .amountInStock(100)
                        .size(Sizes.S)
                        .isActive(true)
                        .measAmount(1)
                        .measUnit("piece")
                        .subcategory(Category.builder().id(1).build())
                        .build()
        );
        when(clothesDal.findAll()).thenReturn(List.of(addedClothes1,
                addedClothes2));
        GetClothesRequest request =
                GetClothesRequest.builder().size(Optional.of("S")).build();
        // Act 
        GetClothesResponse response = clothesService.getAllClothes(request,
                true);
        // Assert 
        assertNotNull(response);
        assertEquals(expectedClothes, response.getFoundClothes());
    }
    @Test
    public void getAllClothesWithSubcategory1ShouldReturn2_When2SuchItemsExist(){
        // Arrange 
        CategoryEntity subCategory = CategoryEntity.builder().id(1).build();
        ClothesEntity addedClothes1 = ClothesEntity
                .builder()
                .id(1)
                .name("new t-shirt")
                .description("new desc")
                .price(5.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(subCategory)
                .build();
        ClothesEntity addedClothes2 = ClothesEntity
                .builder()
                .id(2)
                .name("new purse")
                .description("new purse desc")
                .price(25.99)
                .amountInStock(100)
                .sizesEntity(SizesEntity.S)
                .isActive(true)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(subCategory)
                .build();
        Map<Integer, Clothes> expectedClothes = Map.of(
                1,
                Clothes
                        .builder()
                        .id(1)
                        .name("new t-shirt")
                        .description("new desc")
                        .price(5.99)
                        .amountInStock(100)
                        .size(Sizes.S)
                        .isActive(true)
                        .measAmount(1)
                        .measUnit("piece")
                        .subcategory(Category.builder().id(1).build())
                        .build(),
                2,
                Clothes
                        .builder()
                        .id(2)
                        .name("new purse")
                        .description("new purse desc")
                        .price(25.99)
                        .amountInStock(100)
                        .size(Sizes.S)
                        .isActive(true)
                        .measAmount(1)
                        .measUnit("piece")
                        .subcategory(Category.builder().id(1).build())
                        .build()
        );
        when(clothesDal.findAll()).thenReturn(List.of(addedClothes1,
                addedClothes2));
        GetClothesRequest request =
                GetClothesRequest.builder().subcategoryId(Optional.of(1)).build();
        // Act 
        GetClothesResponse response = clothesService.getAllClothes(request,
                true);
        // Assert 
        assertNotNull(response);
        assertEquals(expectedClothes, response.getFoundClothes());
    }
}
