package ru.dmitry.seleznev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.dmitry.seleznev.model.User;
import ru.dmitry.seleznev.service.UserService;

@Controller
public class PageController {

    private UserService userService;

    @Autowired
    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String admin(Model model, Authentication auth) {
        User loggedUser = userService.getUser(auth.getName());
        model.addAttribute("loggedUser", loggedUser);
        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model, Authentication auth) {
        User loggedUser = userService.getUser(auth.getName());
        model.addAttribute("loggedUser", loggedUser);
        return "user";
    }
}
