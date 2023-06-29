package fontys.s3.andreipieleanu.servicelayer.converters;

import fontys.s3.andreipieleanu.datalayer.entities.CategoryEntity;
import fontys.s3.andreipieleanu.datalayer.entities.ClothesEntity;
import fontys.s3.andreipieleanu.datalayer.entities.SizesEntity;
import fontys.s3.andreipieleanu.domain.Category;
import fontys.s3.andreipieleanu.domain.Clothes;
import fontys.s3.andreipieleanu.domain.Sizes;

public class ClothesConverter {
    private ClothesConverter(){}
    public static Clothes convert(ClothesEntity entity){
        return Clothes
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .amountInStock(entity.getAmountInStock())
                .isActive(entity.isActive())
                .size(Sizes.getSize(entity.getSizesEntity().getValue()))
                .measAmount(entity.getMeasAmount())
                .measUnit(entity.getMeasUnit())
                .subcategory(Category.builder().id(entity.getSubcategoryEntity().getId()).name(entity.getSubcategoryEntity().getName()).build())
                .build();
    }
    public static ClothesEntity convert(Clothes clothes){
        return ClothesEntity
                .builder()
                .id(clothes.getId())
                .name(clothes.getName())
                .description(clothes.getDescription())
                .price(clothes.getPrice())
                .amountInStock(clothes.getAmountInStock())
                .isActive(clothes.getIsActive())
                .sizesEntity(SizesEntity.getSize(clothes.getSize().getValue()))
                .measAmount(clothes.getMeasAmount())
                .measUnit(clothes.getMeasUnit())
                .subcategoryEntity(CategoryEntity.builder().id(clothes.getSubcategory().getId()).name(clothes.getSubcategory().getName()).build())
                .build();
    }
}
