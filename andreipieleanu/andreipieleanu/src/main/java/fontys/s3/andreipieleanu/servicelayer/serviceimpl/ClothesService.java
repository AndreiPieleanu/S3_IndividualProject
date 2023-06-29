package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.IClothesDal;
import fontys.s3.andreipieleanu.datalayer.entities.CategoryEntity;
import fontys.s3.andreipieleanu.datalayer.entities.ClothesEntity;
import fontys.s3.andreipieleanu.datalayer.entities.SizesEntity;
import fontys.s3.andreipieleanu.domain.Clothes;
import fontys.s3.andreipieleanu.domain.requestresponse.clothes.*;
import fontys.s3.andreipieleanu.servicelayer.IClothesService;
import fontys.s3.andreipieleanu.servicelayer.converters.ClothesConverter;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.ClothesNotFoundException;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.DuplicatedClothesException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class ClothesService implements IClothesService {
    private final IClothesDal clothesDal;
    @Override
    @Transactional
    public CreateClothesResponse createClothes(CreateClothesRequest request) {
        List<ClothesEntity> allClothes =
                clothesDal.findAll();
        ClothesEntity clothesToAdd = ClothesEntity
                .builder()
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
        if(allClothes.contains(clothesToAdd)) {
            throw new DuplicatedClothesException();
        }
        ClothesEntity addedEntity =  clothesDal.save(clothesToAdd);
        return new CreateClothesResponse(addedEntity.getId());
    }

    @Override
    @Transactional
    public UpdateClothesResponse updateClothes(UpdateClothesRequest request) {
        ClothesEntity clothesToEdit = ClothesEntity
                .builder()
                .id(request.getId())
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
        if(!clothesDal.existsById(clothesToEdit.getId())) {
            throw new ClothesNotFoundException();
        }
        Integer updatedId = clothesDal.updateClothes(clothesToEdit);
        return new UpdateClothesResponse(updatedId);
    }

    @Override
    public GetClothesResponse getAllClothes(GetClothesRequest request, boolean isActive) {
        List<ClothesEntity> allClothes =
                clothesDal
                        .findAll()
                        .stream()
                        .filter(clothesEntity -> {
                            if(request.getMin().isPresent())
                                return clothesEntity.getPrice() >= request.getMin().get();
                            return true;
                        })
                        .filter(clothesEntity -> {
                            if(request.getMax().isPresent())
                                return clothesEntity.getPrice() <= request.getMax().get();
                            return true;
                        })
                        .filter(clothesEntity -> {
                            if(request.getSize().isPresent())
                                return clothesEntity.getSizesEntity().toString().equals(request.getSize().get());
                            return true;
                        })
                        .filter(clothesEntity -> {
                            if(request.getSubcategoryId().isPresent())
                                return Objects.equals(clothesEntity.getSubcategoryEntity().getId(), request.getSubcategoryId().get());
                            return true;
                        })
                        .toList();
        Map<Integer, Clothes> convertedClothes = new HashMap<>();
        allClothes.forEach((c) -> {
            if(c.isActive() == isActive) {
                convertedClothes.put(c.getId(), ClothesConverter.convert(c));
            }
        });
        return new GetClothesResponse(convertedClothes);
    }

    @Override
    public FindOneClothResponse getClothesById(FindOneClothRequest request) {
        Optional<ClothesEntity> foundClothesEntity =
                clothesDal.findById(request.getClothesId());
        if(foundClothesEntity.isEmpty()){
            throw new ClothesNotFoundException();
        }
        return new FindOneClothResponse(ClothesConverter.convert(foundClothesEntity.get()));
    }

    @Override
    public void deleteClothesById(FindOneClothRequest request) {
        clothesDal.deleteById(request.getClothesId());
    }
}
