package ru.skillbox.demo.dataInitializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import ru.skillbox.demo.entity.User;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.skillbox.demo.service.UserService;

@Component
@ConditionalOnExpression("${run.init:true}")
@RequiredArgsConstructor
public class TestDataInitializer {

    private final UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    @Order(1)
    public void initUsers() {
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setName("name" + i);
            user.setLastname("lname" + i);
            user.setEmail("user" + i + "@skillbox.ru");
            user.setPassword(String.valueOf(i).repeat(4));
            userService.createUser(user);
        }
        for (int i = 1; i < 10; i++) {
            userService.subscribe(i, ++i);
        }
    }
}
