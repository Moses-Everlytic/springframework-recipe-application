package com.nexstudio.recipe.services;

import java.util.Optional;

import com.nexstudio.recipe.commands.IngredientCommand;
import com.nexstudio.recipe.converters.IngredientToIngredientCommand;
import com.nexstudio.recipe.models.Recipe;
import com.nexstudio.recipe.repositories.RecipeRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceMap implements IngredientService {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;


    public IngredientServiceMap(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.error("Recipe Not Found");
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
            .filter(ingredient -> ingredient.getId().equals(ingredientId))
            .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
            .findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            log.error("Ingredient Not Found");
        }  

        return ingredientCommandOptional.get();
    }
    
}
