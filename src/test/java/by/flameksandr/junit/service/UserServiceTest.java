package by.flameksandr.junit.service;

import by.flameksandr.dto.User;
import by.flameksandr.service.UserService;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    private static final User ANDREY = new User(1, "Andrey", "andrey");
    private static final User ALEKS = new User(2, "Aleks", "password");
    private UserService userService;

    @BeforeAll
    void init() {
        System.out.println("Before all: " + this);
    }

    @BeforeEach
    void prepare() {
        System.out.println("Before each: " + this);
        userService = new UserService();

    }

    @Test
    void usersEmptyIfNoUserAdded() {
        System.out.println("Test 1: " + this);
        var users = userService.getAll();
        assertTrue(users.isEmpty());
    }

    @Test
    void usersSizeIfUserAdded() {
        System.out.println("Test 2: " + this);
        userService.add(ANDREY);
        userService.add(ALEKS);
        var users = userService.getAll();

        assertEquals(2, users.size());
    }

    @Test
    void loginSuccessIfUserExists() {
        userService.add(ALEKS);
        Optional<User> maybeUser = userService.login(ALEKS.getUsername(), ALEKS.getPassword());

        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals(ALEKS, user));
    }

    @Test
    void loginFailIfPasswordIsNotCorrect() {
        userService.add(ANDREY);
        Optional<User> maybeUser = userService.login(ANDREY.getUsername(), "password");
        assertTrue(maybeUser.isEmpty());
    }

    @Test
    void loginFailIfUSerDoesNotExist() {
        userService.add(ANDREY);
        Optional<User> maybeUser = userService.login("Boris", ANDREY.getPassword());
        assertTrue(maybeUser.isEmpty());
    }

    @AfterEach
    void deleteDataFromDatabase() {
        System.out.println("After each: " + this);
    }

    @AfterAll
    void closeConnectionPool() {
        System.out.println("After all: " + this);
    }
}
