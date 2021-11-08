package com.example.springfitness.controllers;


import com.example.springfitness.model.Abonement;
import com.example.springfitness.model.Hall;
import com.example.springfitness.repo.AbonementRepository;
import com.example.springfitness.repo.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class WorkerController {
    @Autowired
    private AbonementRepository abonementRepository;
    @Autowired
    private HallRepository hallRepository;

    @GetMapping("/abonements")
    public String abonMain(Model model){
        Iterable<Abonement> abonements = abonementRepository.findAll();
        model.addAttribute("abonements", abonements);
        return "abonements-list";
    }
    @GetMapping("/halls")
    public String hallMain(Model model){
        Iterable<Hall> halls = hallRepository.findAll();
        model.addAttribute("halls", halls);
        return "hall-list";
    }


}
