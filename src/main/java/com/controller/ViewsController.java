package com.controller;

import com.service.IDrinkService;
import com.model.Drink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewsController {

    @Autowired
    private IDrinkService drinkService;

    @GetMapping("/drinks")
    public String findDrinks(Model model) {

        List<Drink> drinks = (List<Drink>) drinkService.findAll();

        model.addAttribute("drinks", drinks);

        return "showDrinks";
    }
}