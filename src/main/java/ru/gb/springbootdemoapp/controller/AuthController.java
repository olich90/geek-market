package ru.gb.springbootdemoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.springbootdemoapp.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AuthController {

    private final UserService userService;
    private final String ERROR = "error";
    private final String MISMATCH_PASSWORD = "Введённые пароли не совпадают!";
    private final String INVALID_EMAIL = "Введён некорректный email!";

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String repeatPassword, Model model) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            model.addAttribute(ERROR, INVALID_EMAIL);
            return "register-error";
        }
        if (!password.equals(repeatPassword)) {
            model.addAttribute(ERROR, MISMATCH_PASSWORD);
            return "register-error";
        }
        try {
            userService.sighUp(username, password);
        } catch (Exception e) {
            model.addAttribute(ERROR, e.getMessage());
            return "register-error";
        }
        return "register-confirm";
    }

    @GetMapping("/register/confirm")
    public String registerConfirm(@RequestParam String token) {
        // TODO токен истек - что делать
        if (userService.confirmRegistration(token)) {
            return "register-complete";
        }
        // TODO что-то выдавать разумное
        return "redirect:/";
    }
}
