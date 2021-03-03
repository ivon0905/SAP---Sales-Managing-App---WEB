package com.example.demo;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    @Mock
    private UserRepository repository;

    @Autowired
    @Mock
    private TestEntityManager entityManager;

//    @Test
//    public void testCreateUser(){
//        User user = new User("Iliq","2222","ilco@gmail.com",222);
//        User savedUser = (User) repository.save(user);
//
//        User existUser = entityManager.find(User.class, savedUser.getId());
//        assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
//    }

    @Test
    public void testFindUser(){
        String username = "iva";
        User user = repository.findByUsername(username);
        assertThat(user).isNotNull();
    }
}
