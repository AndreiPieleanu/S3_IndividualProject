package fontys.s3.andreipieleanu.domain.requestresponse.clothes;

import lombok.*;

import java.util.Optional;

@Builder
@Getter
@EqualsAndHashCode
public class GetClothesRequest {
    @Builder.Default
    private Optional<Double> min = Optional.empty();
    @Builder.Default
    private Optional<Double> max = Optional.empty();
    @Builder.Default
    private Optional<Integer> subcategoryId = Optional.empty();
    @Builder.Default
    private Optional<String> size = Optional.empty();
}
