package ru.dmitry.seleznev.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.dmitry.seleznev.model.Role;
import ru.dmitry.seleznev.model.User;
import ru.dmitry.seleznev.service.UserService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PopulateDb {

    private final UserService userService;

    @Autowired
    public PopulateDb(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        User admin = new User("admin", "admin", "admin@mail.ru", "admin", 99,
                Stream.of(new Role("ROLE_USER"), new Role("ROLE_ADMIN")).collect(Collectors.toSet()));
        userService.saveUser(admin);

        User user = new User("user", "user", "user@mail.ru", "user", 10,
                Stream.of(new Role("ROLE_USER")).collect(Collectors.toSet()));
        userService.saveUser(user);
    }
}
