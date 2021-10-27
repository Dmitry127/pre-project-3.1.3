package ru.dmitry.seleznev.service;

import ru.dmitry.seleznev.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    User getUser(long id);

    User getUser(String email);

    List<User> getAllUsers();

    void updateUser(User user);

    void deleteUser(long id);

    void deleteUser(String email);

}
