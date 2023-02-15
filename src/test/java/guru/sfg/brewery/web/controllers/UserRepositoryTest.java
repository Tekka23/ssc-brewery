package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void isShouldSave() {
        User ADMIN = User.builder()
                .id(null)
                .authority(new Authority(1, "ADMIN", null))
                .password("12345")
                .username("spring").build();

        User USER = User.builder()
                .id(null)
                .authority(new Authority(21, "USER", null))
                .password("982347")
                .username("lox").build();

        User user = userRepository.save(ADMIN);
        userRepository.save(USER);
        Optional<User> testUser = userRepository.findById(1);
        Optional<User> checkId = userRepository.findByUsername(USER.getUsername());

        assertThat(checkId.get().getId())
                .isEqualTo(21L);

        assertThat(testUser)
                .isPresent()
                .hasValueSatisfying(c -> assertThat(c).isEqualTo(user).usingRecursiveComparison());
    }
}
