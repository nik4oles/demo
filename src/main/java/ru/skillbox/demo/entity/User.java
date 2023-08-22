package ru.skillbox.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@NamedEntityGraphs({
        @NamedEntityGraph( name = "userForSubscribeGraph",
                attributeNodes = {@NamedAttributeNode(value = "subscribers")})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    @OneToMany
    private List<User> subscribers;


}
