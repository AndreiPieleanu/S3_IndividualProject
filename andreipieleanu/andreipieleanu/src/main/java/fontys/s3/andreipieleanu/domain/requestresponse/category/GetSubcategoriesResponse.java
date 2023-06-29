package fontys.s3.andreipieleanu.domain.requestresponse.category;

import fontys.s3.andreipieleanu.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSubcategoriesResponse {
    private Map<Integer, Category> categories;
}
