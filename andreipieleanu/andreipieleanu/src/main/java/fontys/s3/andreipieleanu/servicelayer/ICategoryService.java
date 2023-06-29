package fontys.s3.andreipieleanu.servicelayer;

import fontys.s3.andreipieleanu.domain.requestresponse.category.GetSubcategoriesResponse;

public interface ICategoryService {
    GetSubcategoriesResponse getAllSubcategories();
}
