package ru.dmitry.seleznev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmitry.seleznev.model.User;
import ru.dmitry.seleznev.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class MyRestController {

    private UserService userService;

    @Autowired
    public MyRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> list = userService.getAllUsers();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PostMapping("/{role}")
    public ResponseEntity<?> newUser(@PathVariable String role, @RequestBody User user) {
        userService.saveUser(user, role);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{role}")
    public ResponseEntity<?> editUser(@PathVariable String role, @RequestBody User user) {
        userService.updateUser(user, role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
