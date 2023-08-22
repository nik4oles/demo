package ru.skillbox.demo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import ru.skillbox.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    @EntityGraph(value = "userForSubscribeGraph")
    User getUserById(long id);
}
