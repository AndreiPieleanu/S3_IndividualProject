package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.CategoryEntity;
import fontys.s3.andreipieleanu.datalayer.entities.ClothesEntity;
import fontys.s3.andreipieleanu.datalayer.entities.SizesEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IClothesDalTest {
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private IClothesDal clothesDal;
    
    @Test
    public void addClothesShouldAdd_WhenClothesIsNew(){
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name("T-Shirts")
                .build();
        entityManager.persist(categoryEntity);
        ClothesEntity clothesToAdd = ClothesEntity
                .builder()
                .sizesEntity(SizesEntity.S)
                .name("T-Shirt")
                .description("A T-shirt")
                .price(9.99)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(categoryEntity)
                .isActive(true)
                .amountInStock(100)
                .build();
        entityManager.persist(clothesToAdd);
        entityManager.flush();
        entityManager.clear();
        
        ClothesEntity actualClothes = entityManager.find(ClothesEntity.class,
                clothesToAdd.getId());
        ClothesEntity expectedClothes = ClothesEntity
                .builder()
                .id(1)
                .sizesEntity(SizesEntity.S)
                .name("T-Shirt")
                .description("A T-shirt")
                .price(9.99)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(categoryEntity)
                .isActive(true)
                .amountInStock(100)
                .build();
        assertEquals(expectedClothes, actualClothes);
    }
    @Test
    public void updateClothes_ShouldUpdateExistingClothes() {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name("T-Shirts")
                .build();
        entityManager.persist(categoryEntity);
        ClothesEntity clothesToAdd = ClothesEntity
                .builder()
                .sizesEntity(SizesEntity.S)
                .name("T-Shirt")
                .description("A T-shirt")
                .price(9.99)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(categoryEntity)
                .isActive(true)
                .amountInStock(100)
                .build();
        entityManager.persist(clothesToAdd);
        entityManager.flush();
        entityManager.clear();
        clothesDal.save(clothesToAdd);

        ClothesEntity clothesToUpdate = clothesDal.findById(clothesToAdd.getId()).orElse(null);
        assertNotNull(clothesToUpdate);
        clothesToUpdate.setName("Updated T-Shirt");
        clothesToUpdate.setDescription("An updated T-shirt");
        clothesToUpdate.setPrice(14.99);
        clothesDal.updateClothes(clothesToUpdate);
        ClothesEntity updatedClothes = clothesDal.findById(clothesToAdd.getId()).orElse(null);
        assertNotNull(updatedClothes);
        assertEquals(clothesToUpdate, updatedClothes);
    }

    @Test
    public void deleteClothes_ShouldDeleteExistingClothes() {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name("T-Shirts")
                .build();
        entityManager.persist(categoryEntity);
        ClothesEntity clothesToAdd = ClothesEntity
                .builder()
                .sizesEntity(SizesEntity.S)
                .name("T-Shirt")
                .description("A T-shirt")
                .price(9.99)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(categoryEntity)
                .isActive(true)
                .amountInStock(100)
                .build();
        entityManager.persist(clothesToAdd);
        entityManager.flush();
        entityManager.clear();
        clothesDal.save(clothesToAdd);

        ClothesEntity clothesToDelete = clothesDal.findById(clothesToAdd.getId()).orElse(null);
        assertNotNull(clothesToDelete);
        clothesDal.delete(clothesToDelete);

        ClothesEntity deletedClothes = clothesDal.findById(clothesToAdd.getId()).orElse(null);
        assertNull(deletedClothes);
    }

    @Test
    public void getClothesById_ShouldReturnExistingClothes() {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name("T-Shirts")
                .build();
        entityManager.persist(categoryEntity);
        ClothesEntity clothesToAdd = ClothesEntity
                .builder()
                .sizesEntity(SizesEntity.S)
                .name("T-Shirt")
                .description("A T-shirt")
                .price(9.99)
                .measAmount(1)
                .measUnit("piece")
                .subcategoryEntity(categoryEntity)
                .isActive(true)
                .amountInStock(100)
                .build();
        entityManager.persist(clothesToAdd);
        entityManager.flush();
        entityManager.clear();
        clothesDal.save(clothesToAdd);

        ClothesEntity actualClothes = clothesDal.findById(clothesToAdd.getId()).orElse(null);
        assertNotNull(actualClothes);
        assertEquals(clothesToAdd, actualClothes);
    }
}