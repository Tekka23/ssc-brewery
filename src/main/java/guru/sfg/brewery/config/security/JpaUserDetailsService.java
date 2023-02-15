package guru.sfg.brewery.config.security;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        log.debug("Getting user with JPA");

        User user = userRepository.findByUsername(s).orElseThrow(() -> {
            return new UsernameNotFoundException("Username :" +  s + " not found");
        });


        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNotExpired(),
                user.getCredentialsNotExpired(),
                user.getIsAccountNotLocked(),
                convertToSpringAuthorities(user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<Authority> authorities) {
        if (authorities != null && authorities.size() > 0)
            return authorities.stream()
                    .map(Authority::getPermission)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        else return new HashSet<>();
    }
}
