package com.fcano.learningspringboot.it;

import com.fcano.learningspringboot.clientproxy.UserResourceV1;
import com.fcano.learningspringboot.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserIT {

    @Autowired
    private UserResourceV1 userResourceV1;

    @Test
    void shouldInsertUser() {
        // Given
        UUID userUuid = UUID.randomUUID();
        User user = new User(userUuid, "Joe", "Jones", User.Gender.MALE,
                22, "joe.jones@gmail.com");

        // When
        userResourceV1.insertNewUser(user);

        // Then
        User joe = userResourceV1.fetchUser(userUuid);
        assertThat(joe).isEqualToComparingFieldByField(user);
    }

    @Test
    void shouldDeleteUser() {
        // Given
        UUID userUuid = UUID.randomUUID();
        User user = new User(userUuid, "Joe", "Jones", User.Gender.MALE,
                22, "joe.jones@gmail.com");

        // When
        userResourceV1.insertNewUser(user);

        // Then
        User joe = userResourceV1.fetchUser(userUuid);
        assertThat(joe).isEqualToComparingFieldByField(user);

        // When
        userResourceV1.deleteUser(userUuid);

        // Then
        assertThatThrownBy( () -> userResourceV1.fetchUser(userUuid))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldUpdateUser() {
        // Given
        UUID userUuid = UUID.randomUUID();
        User user = new User(userUuid, "Joe", "Jones", User.Gender.MALE,
                22, "joe.jones@gmail.com");

        // When
        userResourceV1.insertNewUser(user);

        // Given
        User updatedUser = new User(userUuid, "Alex", "Jones", User.Gender.MALE,
                55, "alex.jones@gmail.com");

        userResourceV1.updateUser(updatedUser);

        // Then
        user = userResourceV1.fetchUser(userUuid);
        assertThat(user).isEqualToComparingFieldByField(updatedUser);
    }

    @Test
    void shouldFetchUsersByGender() {
        // Given
        UUID userUuid = UUID.randomUUID();
        User user = new User(userUuid, "Joe", "Jones", User.Gender.MALE,
                22, "joe.jones@gmail.com");

        // When
        userResourceV1.insertNewUser(user);

        List<User> females = userResourceV1.fetchUsers(User.Gender.FEMALE.name());

        assertThat(females).extracting("userUid").doesNotContain(userUuid);
        assertThat(females).extracting("firstName").doesNotContain(user.getFirstName());
        assertThat(females).extracting("lastName").doesNotContain(user.getLastName());
        assertThat(females).extracting("gender").doesNotContain(user.getGender());
        assertThat(females).extracting("age").doesNotContain(user.getAge());
        assertThat(females).extracting("email").doesNotContain(user.getEmail());

        List<User> males = userResourceV1.fetchUsers(User.Gender.MALE.name());

        assertThat(males).extracting("userUid").contains(userUuid);
        assertThat(males).extracting("firstName").contains(user.getFirstName());
        assertThat(males).extracting("lastName").contains(user.getLastName());
        assertThat(males).extracting("gender").contains(user.getGender());
        assertThat(males).extracting("age").contains(user.getAge());
        assertThat(males).extracting("email").contains(user.getEmail());
    }
}