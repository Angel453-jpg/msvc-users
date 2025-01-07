package com.angel.springcloud.msvc.users.controllers;

import com.angel.springcloud.msvc.users.entities.User;
import com.angel.springcloud.msvc.users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

        Optional<?> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "Usuario con ID: " + id + " no encontrado!")));

    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {

        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.orElseThrow());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("message", "Usuario con username: " + username + " no encontrado!"));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long id) {

        Optional<?> userUpdatedOptional = userService.update(user, id);

        return userUpdatedOptional.map(userUpdated -> ResponseEntity.status(HttpStatus.CREATED).body(userUpdated))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "Usuario con ID: " + id + " no encontrado!")));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        Optional<User> user = userService.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Usuario con ID: " + id + " no encontrado!"));
        }

        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonMap("message", "Usuario con " + id + " eliminado!"));

    }

}
