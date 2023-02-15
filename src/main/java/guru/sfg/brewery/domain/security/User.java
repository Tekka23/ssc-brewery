package guru.sfg.brewery.domain.security;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", initialValue = 1, allocationSize = 20)
    private Integer id;

    private String username;
    private String password;

//    @Singular
//    @ManyToMany(cascade = CascadeType.MERGE)
//    @JoinTable(name = "user_authority",
//            joinColumns = {@JoinColumn (name = "USER_ID", referencedColumnName = "ID")},
//            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")}   )

    @Transient
    private Set<Authority> authorities;

    public Set<Authority> getAuthorities() {
        return this.roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn (name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")}   )
    private Set<Role> roles;

    @Builder.Default
    private Boolean accountNotExpired = true;
    @Builder.Default
    private Boolean isAccountNotLocked = true;
    @Builder.Default
    private Boolean credentialsNotExpired = true;
    @Builder.Default
    private Boolean enabled = true;
}
