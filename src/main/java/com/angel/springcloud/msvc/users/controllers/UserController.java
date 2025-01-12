package com.angel.springcloud.msvc.users.controllers;

import com.angel.springcloud.msvc.users.entities.User;
import com.angel.springcloud.msvc.users.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

        logger.info("UserController::getUserById, buscando usuario con ID: {}", id);
        Optional<?> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "Usuario con ID: " + id + " no encontrado!")));

    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {

        logger.info("UserController::getUserByUsername, login con: {}", username);
        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.orElseThrow());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("message", "Usuario con username: " + username + " no encontrado!"));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        logger.info("UserController::getAllUsers, listando todos los usuarios");
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {

        logger.info("UserController::createUser, creando: {}", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long id) {

        logger.info("UserController::updateUser, actualizando: {}", user);
        Optional<?> userUpdatedOptional = userService.update(user, id);

        return userUpdatedOptional.map(userUpdated -> ResponseEntity.status(HttpStatus.CREATED).body(userUpdated))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "Usuario con ID: " + id + " no encontrado!")));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        logger.info("UserController::deleteUser, eliminando usuario con ID: {}", id);
        Optional<User> user = userService.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Usuario con ID: " + id + " no encontrado!"));
        }

        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonMap("message", "Usuario con " + id + " eliminado!"));

    }

}
