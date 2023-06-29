package fontys.s3.andreipieleanu.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class UserRole {
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Integer id;
    private Role role;
}
