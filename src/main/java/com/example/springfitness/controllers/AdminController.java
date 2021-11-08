package com.example.springfitness.controllers;


import com.example.springfitness.model.Abonement;
import com.example.springfitness.model.Hall;
import com.example.springfitness.model.Role;
import com.example.springfitness.model.User;
import com.example.springfitness.repo.AbonementRepository;
import com.example.springfitness.repo.HallRepository;
import com.example.springfitness.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
//Разграничение прав
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {

    //Репозитории
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AbonementRepository abonementRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HallRepository hallRepository;

    //Вывод списка пользователей
    @GetMapping("/user-list")
    public String userList(Model model){
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }
    //Удаление пользователя
    @GetMapping("/user-list/{id}/delete")
    public String userDelete(@PathVariable(value = "id") Long id) {
        User u = userRepository.findById(id).orElseThrow();
        userRepository.delete(u);
        return "redirect:/admin/user-list";
    }
    //GET для добавления
    @GetMapping("/user-add")
    public String userAddGet(User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-add";
    }
    //POST для добавления
    @PostMapping("/user-add")
    public String userAddPost(@Valid User user, BindingResult bindingResult, Model model,
                              @RequestParam(name = "roles[]", required = false) String[] roles) {
        User userDB = userRepository.findByUsername(user.getUsername());
        model.addAttribute("roles", Role.values());
        if (userDB != null) {
            model.addAttribute("message", "Такой пользователь уже существует!");
            return "user-add";
        }
        if (bindingResult.hasErrors()) {
            return "user-add";
        }
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        if (roles != null) {
            Arrays.stream(roles).forEach(r -> user.getRoles().add(Role.valueOf(r)));
        }
        userRepository.save(user);
        return "redirect:/admin/user-list";
    }

    //GET для редактирования
    @GetMapping("/user-edit/{id}")
    public String userEditGet(Model model, @PathVariable(value = "id") Long id, User user) {
        Optional<User> s = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        s.ifPresent(res::add);
        model.addAttribute("user", res);
        model.addAttribute("userValid", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }
    //POST для редактирования
    @PostMapping("/user-edit/{id}")
    public String userEditPost(@PathVariable(value = "id") Long id,
                               @Valid @ModelAttribute("userValid") User user,
                               BindingResult bindingResult,
                               @RequestParam(name = "roles[]", required = false) String[] roles, Model model) {

        Optional<User> s = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        s.ifPresent(res::add);
        model.addAttribute("user", res);
        model.addAttribute("roles", Role.values());
        if (bindingResult.hasFieldErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("userValid", user);
            return "user-edit";
        }
        //Изменение
        User u = userRepository.findById(id).orElseThrow();
        u.getRoles().clear();
        if (roles != null) {
            Arrays.stream(roles).forEach(r -> u.getRoles().add(Role.valueOf(r)));
        }
        u.setUsername(user.getUsername());
        userRepository.save(u);
        return "redirect:/admin/user-list";
    }

    //Методы для абонемента
    //Вывод списка
    @GetMapping("/abonements-admin")
    public String abonementList(Model model) {
        Iterable<Abonement> abonements = abonementRepository.findAll();
        model.addAttribute("abonement", abonements);
        return "abonements-admin";
    }

    //GET для добавления
    @GetMapping("/abonement-add")
    public String abonementAddGet(Model model, Abonement abonement) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("abonement", abonement);
        return "abonement-add";
    }
    //POST для добавления
    @PostMapping("/abonement-add")
    public String abonementAddPost(Model model, @Valid @ModelAttribute("abonement") Abonement abonement, BindingResult bindingResult) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("abonement", abonement);
        //Добавление
        if (bindingResult.hasFieldErrors()) {
            return "abonement-add";
        }
        Abonement f = new Abonement(abonement.getNameAbon(),
                abonement.getCost(),
                abonement.getCount());
        abonementRepository.save(f);
        return "redirect:/admin/abonements-admin";
    }

    //GET для удаления
    @GetMapping("/abonements-admin/{id}/delete")
    public String abonementDelete(@PathVariable(value = "id") Long id) {
        Abonement f = abonementRepository.findById(id).orElseThrow();
        abonementRepository.delete(f);
        return "redirect:/admin/abonements-admin";
    }

    //GET для редактирования
    @GetMapping("/abonement-edit/{id}")
    public String abonementEditGet(@PathVariable(value = "id") Long id, Model model, Abonement abonement) {
        Optional<Abonement> abon = abonementRepository.findById(id);
        ArrayList<Abonement> res = new ArrayList<>();
        abon.ifPresent(res::add);
        model.addAttribute("abonement", res);
        model.addAttribute("abonementValid", abonement);
        return "abonement-edit";
    }

    //POST для редактирования
    @PostMapping("/abonement-edit/{id}")
    public String abonementEditPost(@PathVariable(value = "id") Long id,
                               @Valid @ModelAttribute("abonValid") Abonement abonement,
                               BindingResult bindingResult,
                               Model model) {
        Optional<Abonement> f = abonementRepository.findById(id);
        ArrayList<Abonement> res = new ArrayList<>();
        f.ifPresent(res::add);
        model.addAttribute("abonement", res);
        if (bindingResult.hasFieldErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("abonementValid", abonement);
            return "abonement-edit";
        }
        //Изменение
        Abonement abon = abonementRepository.findById(id).orElseThrow();
        abon.setNameAbon(abonement.getNameAbon());
        abon.setCost(abonement.getCost());
        abon.setCount(abonement.getCount());
        abonementRepository.save(abon);
        return "redirect:/admin/abonements-admin";
    }


    //Методы для залов
    //Вывод списка
    @GetMapping("/hall-list")
    public String hallList(Model model) {
        Iterable<Hall> halls = hallRepository.findAll();
        model.addAttribute("halls", halls);
        return "hall-list";
    }

    //GET для добавления
    @GetMapping("/hall-add")
    public String hallAddGet(Model model, Hall hall) {
        Iterable<Hall> halls = hallRepository.findAll();
        model.addAttribute("hall", halls);
        return "hall-add";
    }
    //POST для добавления
    @PostMapping("/hall-add")
    public String hallAddPost(Model model, @Valid @ModelAttribute("hall") Hall hall, BindingResult bindingResult) {
        Iterable<Hall> halls = hallRepository.findAll();
        model.addAttribute("hall", halls);
        //Добавление
        if (bindingResult.hasFieldErrors()) {
            return "hall-add";
        }
        Hall f = new Hall(hall.getName(),
                hall.getCountPeople(),
                hall.getSity());
        hallRepository.save(f);
        return "redirect:/admin/hall-list";
    }

    //GET для удаления
    @GetMapping("/hall-list/{id}/delete")
    public String hallDelete(@PathVariable(value = "id") Long id) {
        Hall f = hallRepository.findById(id).orElseThrow();
        hallRepository.delete(f);
        return "redirect:/admin/hall-list";
    }

    //GET для редактирования
    @GetMapping("/hall-edit/{id}")
    public String hallEditGet(@PathVariable(value = "id") Long id, Model model, Hall hall) {
        Optional<Hall> hal = hallRepository.findById(id);
        ArrayList<Hall> res = new ArrayList<>();
        hal.ifPresent(res::add);
        model.addAttribute("hall", res);
        model.addAttribute("hallValid", hall);
        return "hall-edit";
    }

    //POST для редактирования
    @PostMapping("/hall-edit/{id}")
    public String hallEditPost(@PathVariable(value = "id") Long id,
                               @Valid @ModelAttribute("abonValid") Hall hall,
                               BindingResult bindingResult,
                               Model model) {
        Optional<Hall> f = hallRepository.findById(id);
        ArrayList<Hall> res = new ArrayList<>();
        f.ifPresent(res::add);
        model.addAttribute("hall", res);
        if (bindingResult.hasFieldErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("hallValid", hall);
            return "hall-edit";
        }
        //Изменение
        Hall hal = hallRepository.findById(id).orElseThrow();
        hal.setCountPeople(hall.getCountPeople());
        hal.setName(hall.getName());
        hal.setSity(hall.getSity());
        hallRepository.save(hal);
        return "redirect:/admin/hall-list";
    }


}
