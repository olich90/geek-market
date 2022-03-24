package ru.gb.springbootdemoapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gb.springbootdemoapp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    void test() {
        User user = new User();
        user.setEmail("user11@user.ru");

        User user2 = new User();
        user2.setEmail("user12@user.ru");

        List.of(user, user2).forEach(
                u -> {
                    u.setPassword("");
                    u.setEnabled(false);
                }
        );

        entityManager.persist(user);
        entityManager.persist(user2);

        List<Optional<User>> users = new ArrayList<>();
        users.add(userRepository.findByEmail("user11@user.ru"));
        users.add(userRepository.findByEmail("user12@user.ru"));


        assertEquals(2, users.size());
        assertTrue(users.stream().allMatch(u -> u.get().getEnabled() == false));
    }
}
