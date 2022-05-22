package com.board.boardmaking;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    PasswordEncoder passwordEncoder;



    @Test
    public void 패스워드_암호화() throws Exception{

        String password = "남아연skadkdus";

        String encodePassword1 = passwordEncoder.encode(password);

        String encodePassword2 = passwordEncoder.encode(password);

        assertThat(encodePassword1).isNotEqualTo(encodePassword2);
    }


    @Test
    public void 암호화된_비밀번호_매치() throws Exception {
        String password ="남아연skadkdus";

        String encodePassword1 = passwordEncoder.encode(password);

        assertThat(passwordEncoder.matches(password,encodePassword1)).isTrue();
    }
}
