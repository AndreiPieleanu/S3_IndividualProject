package fontys.s3.andreipieleanu.datalayer;

import fontys.s3.andreipieleanu.datalayer.entities.ClothesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface IClothesDal extends JpaRepository<ClothesEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update ClothesEntity ce set ce.name=:#{#clothesEntity.name}, " +
            "ce.amountInStock=:#{#clothesEntity.amountInStock}, " +
            "ce.description=:#{#clothesEntity.description}, " +
            "ce.price=:#{#clothesEntity.price}, " +
            "ce.sizesEntity=:#{#clothesEntity.sizesEntity}, " +
            "ce.isActive=:#{#clothesEntity.isActive}, " +
            "ce.measAmount=:#{#clothesEntity.measAmount}, " +
            "ce.measUnit=:#{#clothesEntity.measUnit}, " +
            "ce.subcategoryEntity.id=:#{#clothesEntity.subcategoryEntity.id} " +
            "where ce.id=:#{#clothesEntity.id}")
    Integer updateClothes(ClothesEntity clothesEntity);
}
