package com.fcano.learningspringboot.service;

import com.fcano.learningspringboot.dao.FakeDataDao;
import com.fcano.learningspringboot.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


class UserServiceTest {

    @Mock
    private FakeDataDao fakeDataDao;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeDataDao);
    }

    @Test
    void shouldGetAllUsers() {
        UUID userUid = UUID.randomUUID();
        User anna = new User(userUid, "anna", "montana", User.Gender.FEMALE, 30,
                "anna@gmail.com");
        List<User> users = Arrays.asList(anna);

        given(fakeDataDao.selectAllUsers()).willReturn(users);

        List<User> allUsers = userService.getAllUsers(Optional.empty());

        assertThat(allUsers).hasSize(1);

        User user = allUsers.get(0);
        assertAnnaFields(user);
    }

    @Test
    void shouldGetAllUserByGender(){
        UUID userUid = UUID.randomUUID();
        User anna = new User(userUid, "anna", "montana", User.Gender.FEMALE, 30,
                "anna@gmail.com");

        UUID joeUid = UUID.randomUUID();
        User joe = new User(joeUid, "joe", "jones", User.Gender.MALE, 30,
                "joe.jones@gmail.com");
        List<User> users = Arrays.asList(anna, joe);

        given(fakeDataDao.selectAllUsers()).willReturn(users);

        List<User> filteredUsers = userService.getAllUsers(Optional.of("female"));
        assertThat(filteredUsers).hasSize(1);
        assertAnnaFields(filteredUsers.get(0));
    }

    @Test
    void shouldThrowExceptionWhenGenderIsInvalid(){
        assertThatThrownBy(() -> userService.getAllUsers(Optional.of("asdfsdf")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Invalid gender");
    }

    @Test
    void shouldGetUser() {
        UUID annaUuid = UUID.randomUUID();
        User anna = new User(annaUuid, "anna", "montana", User.Gender.FEMALE, 30,
                "anna@gmail.com");
        given(fakeDataDao.selectUserByUserUid(annaUuid)).willReturn(Optional.of(anna));
        Optional<User> userOptional = userService.getUser(annaUuid);

        assertThat(userOptional.isPresent()).isTrue();
        User user = userOptional.get();

        assertAnnaFields(user);

    }

    private void assertAnnaFields(User user) {
        assertThat(user.getAge()).isEqualTo(30);
        assertThat(user.getFirstName()).isEqualTo("anna");
        assertThat(user.getLastName()).isEqualTo("montana");
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getEmail()).isEqualTo("anna@gmail.com");
        assertThat(user.getUserUid()).isNotNull();
        assertThat(user.getUserUid()).isInstanceOf(UUID.class);
    }

    @Test
    void shouldUpdateUser() {
        UUID annaUuid = UUID.randomUUID();
        User anna = new User(annaUuid, "anna", "montana", User.Gender.FEMALE, 30,
                "anna@gmail.com");
        given(fakeDataDao.updateUser(anna)).willReturn(1);
        given(fakeDataDao.selectUserByUserUid(annaUuid)).willReturn(Optional.of(anna));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int updateResult = userService.updateUser(anna);

        verify(fakeDataDao).selectUserByUserUid(annaUuid);
        verify(fakeDataDao).updateUser(captor.capture());

        User user = captor.getValue();
        assertAnnaFields(user);

        assertThat(updateResult).isEqualTo(1);
    }

    @Test
    void shouldRemoveUser() {
        UUID annaUuid = UUID.randomUUID();
        User anna = new User(annaUuid, "anna", "montana", User.Gender.FEMALE, 30,
                "anna@gmail.com");
        given(fakeDataDao.deleteUserByUserUid(annaUuid)).willReturn(1);
        given(fakeDataDao.selectUserByUserUid(annaUuid)).willReturn(Optional.of(anna));

        int updateResult = userService.removeUser(annaUuid);

        verify(fakeDataDao).selectUserByUserUid(annaUuid);
        verify(fakeDataDao).deleteUserByUserUid(annaUuid);

        assertThat(updateResult).isEqualTo(1);
    }

    @Test
    void shouldInsertUser() {
        User anna = new User(null, "anna", "montana", User.Gender.FEMALE, 30,
                "anna@gmail.com");
        given(fakeDataDao.insertUser(any(UUID.class), eq(anna))).willReturn(1);
        int insertResult = userService.insertUser(anna);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());

        User user = captor.getValue();

        assertAnnaFields(user);
        assertThat(insertResult).isEqualTo(1);
    }
}