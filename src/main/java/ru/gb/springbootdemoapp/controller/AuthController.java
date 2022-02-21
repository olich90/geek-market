package ru.gb.springbootdemoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.springbootdemoapp.service.RegisterService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AuthController {

    private final RegisterService registerService;
    private final String ERROR = "error";
    private final String MISMATCH_PASSWORD = "Введённые пароли не совпадают!";
    private final String INVALID_EMAIL = "Введён некорректный email!";

    public AuthController(RegisterService registerService) {
        this.registerService = registerService;
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
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String passwordConfirm, Model model) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            model.addAttribute(ERROR, INVALID_EMAIL);
            return "register";
        }
        if (!password.equals(passwordConfirm)) {
            model.addAttribute(ERROR, MISMATCH_PASSWORD);
            return "register";
        }
        try {
            registerService.sighUp(username, password);
        } catch (IllegalStateException e) {
            model.addAttribute(ERROR, e.getMessage());
            return "register";
        }
        return "register-confirm";
    }

    @GetMapping("/register/confirm")
    public String registerConfirm(@RequestParam String token, Model model) {
        try {
            registerService.confirmRegistration(token);
            return "register-complete";
        } catch (IllegalStateException e) {
            model.addAttribute(ERROR, e.getMessage());
            return "register-error";
        }
    }
}
