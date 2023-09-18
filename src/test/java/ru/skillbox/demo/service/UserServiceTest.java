package ru.skillbox.demo.service;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import ru.skillbox.demo.DemoApplication;
import ru.skillbox.demo.entity.User;
import ru.skillbox.demo.repository.UserRepository;

import java.util.Optional;


@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;


    @Test
    void createUser() {
        // Arrange
        User user = new User();
        user.setId(15L);
        user.setEmail("test@test.com");
        user.setName("John");
        user.setLastname("Doe");
        user.setPassword("123456");

        Mockito.when(userRepository.save(user)).thenReturn(user);

        // Act
        String result = userService.createUser(user);

        // Assert
        Assertions.assertEquals("Пользователь с id = 15 сохранен", result);
    }


    @Test
    void getUser() {
        // Arrange
        long id = 15L;
        User user = new User();
        user.setId(id);
        user.setEmail("test@test.com");
        user.setName("John");
        user.setLastname("Doe");
        user.setPassword("123456");

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUser(id);

        // Assert
        Assertions.assertEquals(user, result);
    }

    @Test
    void getUserNotFoundTest() {
        // Arrange
        long id = 1L;

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.getUser(id));
    }

    @Test
    void updateUser() {
        // Arrange
        long id = 15L;
        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setEmail("test@test.com");
        existingUser.setName("John");
        existingUser.setLastname("Doe");
        existingUser.setPassword("123456");

        User updatedUser = new User();
        updatedUser.setEmail("updated@test.com");
        updatedUser.setName("Updated");
        updatedUser.setLastname("User");
        updatedUser.setPassword("654321");

        Mockito.when(userRepository.existsById(id)).thenReturn(true);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(existingUser)).thenReturn(existingUser);

        // Act
        String result = userService.updateUser(updatedUser, id);

        // Assert
        Assertions.assertEquals("Пользователь с id = 15 обновлен", result);
        Assertions.assertEquals(updatedUser.getEmail(), existingUser.getEmail());
        Assertions.assertEquals(updatedUser.getName(), existingUser.getName());
        Assertions.assertEquals(updatedUser.getLastname(), existingUser.getLastname());
        Assertions.assertEquals(updatedUser.getPassword(), existingUser.getPassword());
    }
    @Test
    void updateUserNotFoundTest() {
        // Arrange
        long id = 1L;
        User updatedUser = new User();
        updatedUser.setEmail("updated@test.com");
        updatedUser.setName("Updated");
        updatedUser.setLastname("User");
        updatedUser.setPassword("654321");

        Mockito.when(userRepository.existsById(id)).thenReturn(false);

        // Act and Assert
        Assertions.assertThrows(ResponseStatusException.class, () -> userService.updateUser(updatedUser, id));
    }

    @Test
    void deleteUser() {
    }
    @Test
    public void testDeleteUserSuccess() {
        long userId = 1L;

        // Mocking repository method
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);

        String result = userService.deleteUser(userId);


        Assert.assertEquals("Пользователь удален", result);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteUserUserNotFound() {
        long userId = 1L;

        // Mocking repository method
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);


        Assertions.assertThrows(ResponseStatusException.class, () -> userService.deleteUser(userId) );
    }

    @Test
    void subscribe() {
    }

    @Test
    void unsubscribeSuccess() {
        long userId = 1L;
        long subscriberId = 2L;

        User user = new User();
        User subscriber = new User();
        user.getSubscribers().add(subscriber);

        // Mocking repository method
        Mockito.when(userRepository.getUserById(userId)).thenReturn(user);
        Mockito.when(userRepository.getUserById(subscriberId)).thenReturn(subscriber);

        String result = userService.unsubscribe(userId, subscriberId);

        Assert.assertEquals("Успешно", result);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void testUnsubscribeUserNotFound() {
        long userId = 1L;
        long subscriberId = 2L;

        // Mocking repository method
        Mockito.when(userRepository.getUserById(userId)).thenReturn(null);
        Mockito.when(userRepository.getUserById(subscriberId)).thenReturn(null);



        Assertions.assertThrows(ResponseStatusException.class, () -> userService.unsubscribe(userId, subscriberId));

    }

    @Test
    public void testUnsubscribeUserNotSubscribed() {
        long userId = 2L;
        long subscriberId = 1L;

        User user = new User();
        User subscriber = new User();

        // Mocking repository method
        Mockito.when(userRepository.getUserById(userId)).thenReturn(user);
        Mockito.when(userRepository.getUserById(subscriberId)).thenReturn(subscriber);


        Assertions.assertThrows(RuntimeException.class, () -> userService.unsubscribe(userId, subscriberId));
    }

}
