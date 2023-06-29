package fontys.s3.andreipieleanu.servicelayer;

import fontys.s3.andreipieleanu.domain.requestresponse.clothes.*;

public interface IClothesService {
    CreateClothesResponse createClothes(CreateClothesRequest request);
    UpdateClothesResponse updateClothes(UpdateClothesRequest request);
    GetClothesResponse getAllClothes(GetClothesRequest request,
                                     boolean isActive);
    FindOneClothResponse getClothesById(FindOneClothRequest request);
    void deleteClothesById(FindOneClothRequest request);
}
