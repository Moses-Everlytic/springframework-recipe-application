package com.nexstudio.recipe.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.nexstudio.recipe.models.Recipe;
import com.nexstudio.recipe.repositories.RecipeRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceMap implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceMap(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");

        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long l) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(l);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe Not Found");
        }
        return recipeOptional.get();
    }
}
