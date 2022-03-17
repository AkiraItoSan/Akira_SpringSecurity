package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(User user);

    List<User> listUsers();

    void remove(User user);

    User getUserById(Long id);

    Optional<User> findByEmail(String email);
}
