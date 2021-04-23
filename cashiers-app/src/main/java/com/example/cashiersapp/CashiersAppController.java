package com.example.cashiersapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class CashiersAppController {

    @GetMapping
    public String cashiers() {
        return "cashiers";
    }

    @PostMapping
    public String post() {
        return "cashiers";
    }
}