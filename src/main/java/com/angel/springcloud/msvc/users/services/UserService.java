package com.angel.springcloud.msvc.users.services;

import com.angel.springcloud.msvc.users.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    User save(User user);

    Optional<User> update(User user, Long id);

    void delete(Long id);

}
