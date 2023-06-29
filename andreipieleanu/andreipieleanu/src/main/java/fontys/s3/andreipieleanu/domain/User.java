package fontys.s3.andreipieleanu.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class User {
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @EqualsAndHashCode.Exclude
    private UserRole userRole;
}
