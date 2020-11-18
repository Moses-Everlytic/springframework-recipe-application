package com.nexstudio.recipe.controllers;

import com.nexstudio.recipe.services.RecipeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {
    private final RecipeService recipeService;


    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/index"})
    public String getIndex(Model model) {
        log.debug("Getting index page");
        
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
