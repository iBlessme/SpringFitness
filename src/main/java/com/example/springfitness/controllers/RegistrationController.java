package com.example.springfitness.controllers;

import com.example.springfitness.model.Role;
import com.example.springfitness.model.User;
import com.example.springfitness.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

//Контроллер, ответственный за регистрацию
@Controller
public class RegistrationController {
    //Репозиторий пользователя
    @Autowired
    private UserRepository userRepository;
    //Шифрование
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Метод регистрации
    @GetMapping("/registration")
    public String registrationGet(User user, Model model) {
        model.addAttribute("user", user);
        return "registration";
    }

    //Метод, который регистрирует пользователя
    @PostMapping("/registration")
    public String registrationPost(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        User userDB = userRepository.findByUsername(user.getUsername());
        if (userDB != null) {
            model.addAttribute("message", "Такой пользователь уже существует!");
            return "registration";
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ADMIN));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }
}