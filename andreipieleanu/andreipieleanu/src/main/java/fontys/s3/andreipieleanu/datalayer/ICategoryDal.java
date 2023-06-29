package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICategoryDal extends JpaRepository<CategoryEntity, Integer> {
    @Query("select subcat from CategoryEntity subcat where subcat" +
            ".parentCategory != null")
    List<CategoryEntity> getAllSubcategories();
}
