package ru.skillbox.demo.webappRestController;

import ru.skillbox.demo.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.demo.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    ResponseEntity<String> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUser(@PathVariable long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable long id) {
        return new ResponseEntity<>(userService.updateUser(user, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteUser(@PathVariable long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/subscribe/{subscriberId}")
    ResponseEntity<String> subscribe(@PathVariable long id, @PathVariable long subscriberId) {
        return new ResponseEntity<>(userService.subscribe(id, subscriberId), HttpStatus.OK);
    }

    @PostMapping("/{id}/unsubscribe/{subscriberId}")
    ResponseEntity<String> unsubscribe(@PathVariable long id, @PathVariable long subscriberId) {
        return new ResponseEntity<>(userService.unsubscribe(id, subscriberId), HttpStatus.OK);
    }
}
