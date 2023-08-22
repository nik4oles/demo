package ru.skillbox.demo.service;

import org.springframework.stereotype.Service;
import ru.skillbox.demo.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.skillbox.demo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createUser(User user) {
        User savedUser = userRepository.save(user);
        return String.format("Пользователь с id = %s сохранен", savedUser.getId());
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String updateUser(User user, long id) {
        if(!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User userBD = getUser(id);
        userBD.setEmail(user.getEmail());
        userBD.setName(user.getName());
        userBD.setLastname(user.getLastname());
        userBD.setPassword(user.getPassword());

        userRepository.save(userBD);

        return String.format("Пользователь с id = %s обновлен", id);
    }

    public String deleteUser(long id) {
        if(!userRepository.existsById(id)) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        return String.format("Пользователь удален");
    }

    public String subscribe(long id, long subscriberId) {
        User user = userRepository.getUserById(id);
        User subscriber = userRepository.getUserById(subscriberId);

        if(user == null || subscriber == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if(user.getSubscribers().contains(subscriber)) {
            throw new RuntimeException(String.format("Пользователь  с %s уже подписан на пользователя  с %s", id, subscriberId));
           // throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED);
        }

        user.getSubscribers().add(subscriber);
        userRepository.save(user);
        return "Успешно";
    }

    public String unsubscribe(long id, long subscriberId) {
        User user = userRepository.getUserById(id);
        User subscriber = userRepository.getUserById(subscriberId);

        if(user == null || subscriber == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if(!user.getSubscribers().contains(subscriber)) {
            throw new RuntimeException(String.format("Пользователь с id = %s не подписан на пользователя с id =%s", id, subscriberId));
        }

        user.getSubscribers().remove(subscriber);
        userRepository.save(user);
        return "Успешно";
    }

}
