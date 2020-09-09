package com.nexstudio.recipe.controllers;

import java.util.Optional;

import com.nexstudio.recipe.models.Category;
import com.nexstudio.recipe.models.UnitOfMeasure;
import com.nexstudio.recipe.repositories.CategoryRepository;
import com.nexstudio.recipe.repositories.UnitOfMeasureRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;


    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @RequestMapping({"", "/index"})
    public String getIndex() {

        Optional<Category> categoryOptional = categoryRepository.findByDescription("African");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        System.out.println("Category Id is: " + categoryOptional.get().getId());
        System.out.println("Unit Of Measure Id is: " + unitOfMeasureOptional.get().getId());

        return "index";
    }
}
