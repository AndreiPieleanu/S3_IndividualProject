package fontys.s3.andreipieleanu.datalayer.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cs_users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@EqualsAndHashCode
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Column(name = "user_email")
    @NotNull
    @Length(max = 50)
    private String email;
    @Column(name = "user_firstname")
    @NotNull
    @Length(max = 50)
    private String firstName;
    @Column(name = "user_lastname")
    @NotNull
    @Length(max = 50)
    private String lastName;
    @Column(name = "user_password")
    @NotNull
    @Length(max = 100)
    private String password;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @EqualsAndHashCode.Exclude
    private UserRoleEntity userRole;
}
