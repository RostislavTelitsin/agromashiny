package ru.agromashiny.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {
    @GetMapping
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/bolt")
    public String bolt(Model model) {
        return "bolt";
    }
}