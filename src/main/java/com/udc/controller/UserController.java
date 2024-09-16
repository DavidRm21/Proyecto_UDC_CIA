package com.udc.controller;

import com.udc.model.PersonalData;
import com.udc.model.User;
import com.udc.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String formPage() {
        return "form";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user, Model model) throws Exception {
        return userService.login(user, model);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user, Model model) throws Exception {
        return userService.register(user, model);
    }

    @PostMapping("/form")
    public ResponseEntity<?> form(@RequestBody PersonalData personalData) throws Exception {
        return userService.savePersonalData(personalData);
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) throws Exception {
        return userService.getPersonalData(id);
    }
}
