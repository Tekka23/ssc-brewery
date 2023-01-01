package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PasswordEncoderTest {
    private static final String PASSWORD = "password";

    String salted = PASSWORD + Math.random()*10;
    @Test
    void hashingExample(){
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes(StandardCharsets.UTF_8)));
        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void hashingOfSaltedStr(){
        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes(StandardCharsets.UTF_8)));
    }
    @Test
    void noOpPassEncoder(){
        PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
        System.out.println(encoder.encode(PASSWORD));
    }

    @Test
    void testLdap() {
        PasswordEncoder encoder = new LdapShaPasswordEncoder();
        System.out.println(encoder.encode(PASSWORD));
        System.out.println(encoder.encode(PASSWORD));
    }

    @Test
    void testSHA256() {
        PasswordEncoder encoder = new StandardPasswordEncoder();
        System.out.println(encoder.encode(PASSWORD));
        System.out.println(encoder.encode(PASSWORD));
    }

    @Test
    void testBcrypt() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(PASSWORD));
    }

    @Test
    void isShouldName() {
        System.out.println(UUID.randomUUID());

        System.out.println(UUID.randomUUID());

        System.out.println(UUID.randomUUID());
    }
}
