package fontys.s3.andreipieleanu.servicelayer.converters;

import fontys.s3.andreipieleanu.datalayer.entities.CategoryEntity;
import fontys.s3.andreipieleanu.domain.Category;

public class CategoryConverter {
    private CategoryConverter(){}
    public static Category convert(CategoryEntity entity){
        return Category
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
