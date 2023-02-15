package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.RoleRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class UserDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserDataLoader(AuthorityRepository authorityRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
        this.authorityRepository = authorityRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        loadSecurityData();
    }


    private void loadSecurityData(){
        Authority createBeer = authorityRepository.save(Authority.builder().permission("beer.create").build());
        Authority updateBeer = authorityRepository.save(Authority.builder().permission("beer.update").build());
        Authority readBeer = authorityRepository.save(Authority.builder().permission("beer.read").build());
        Authority deleteBeer = authorityRepository.save(Authority.builder().permission("beer.delete").build());

        Role adminRole = roleRepository.save(Role.builder().name("ADMIN").build());
        Role userRole = roleRepository.save(Role.builder().name("USER").build());
        Role customerRole = roleRepository.save(Role.builder().name("CUSTOMER").build());

        adminRole.setAuthorities(Set.of(createBeer, updateBeer, readBeer, deleteBeer));
        userRole.setAuthorities(Set.of(readBeer));
        customerRole.setAuthorities(Set.of(readBeer));

        roleRepository.saveAll(List.of(adminRole, userRole, customerRole));

        userRepository.save(User.builder()
                .username("spring")
                .password(encoder.encode("guru"))
                .role(adminRole)
                .build());
        userRepository.save(User.builder()
                .username("user")
                .password(encoder.encode("password"))
                .role(userRole)
                .build());
        userRepository.save(User.builder()
                .username("scott")
                .password(encoder.encode("tiger"))
                .role(customerRole)
                .build());

        log.debug("Users loaded" + userRepository.count());
    }
}
