package com.example.springfitness.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

    //Контроллер, основной страницы
    @Controller
    public class MainController {
        @GetMapping("/")
        public String index() {
            return "index";
        }

    }