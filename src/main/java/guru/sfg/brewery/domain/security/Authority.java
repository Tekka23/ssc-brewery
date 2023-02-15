package guru.sfg.brewery.domain.security;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq")
    @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 20)
    private Integer id;

    private String permission;

    @ManyToMany(mappedBy = "roles")
    private Set<Role> roles;
}
