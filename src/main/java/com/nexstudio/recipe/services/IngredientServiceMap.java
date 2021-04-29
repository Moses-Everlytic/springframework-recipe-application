package com.nexstudio.recipe.services;

import java.util.Optional;

import com.nexstudio.recipe.commands.IngredientCommand;
import com.nexstudio.recipe.converters.IngredientCommandToIngredient;
import com.nexstudio.recipe.converters.IngredientToIngredientCommand;
import com.nexstudio.recipe.models.Ingredient;
import com.nexstudio.recipe.models.Recipe;
import com.nexstudio.recipe.repositories.RecipeRepository;
import com.nexstudio.recipe.repositories.UnitOfMeasureRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceMap implements IngredientService {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceMap(
        IngredientToIngredientCommand ingredientToIngredientCommand,
        IngredientCommandToIngredient ingredientCommandToIngredient, 
        RecipeRepository recipeRepository,
        UnitOfMeasureRepository unitOfMeasureRepository
    ) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {
            log.error("Recipe Not Found");
            return new IngredientCommand();
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient>  ingredientOptional = recipe.getIngredients().stream()
            .filter(ingredient -> ingredient.getId().equals(command.getId()))
            .findFirst();
        
        if (ingredientOptional.isPresent()) {
            Ingredient ingredientFound = ingredientOptional.get();

            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setAmount(command.getAmount());
            ingredientFound.setUom(unitOfMeasureRepository.findById(command.getUom().getId())
                .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
        } else {
            recipe.addIngredient(ingredientCommandToIngredient.convert(command));
        }

        Recipe recipeSaved = recipeRepository.save(recipe);

        return ingredientToIngredientCommand.convert(
                recipeSaved.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst()
                    .get()
            );
    }
    
}
