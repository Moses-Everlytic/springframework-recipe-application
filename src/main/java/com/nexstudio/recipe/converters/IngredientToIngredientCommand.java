package com.nexstudio.recipe.converters;

import com.nexstudio.recipe.commands.IngredientCommand;
import com.nexstudio.recipe.models.Ingredient;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import lombok.Synchronized;

public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ingredient.getId());
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setDescription(ingredient.getDescription());

        
        ingredientCommand.setUnitOfMeasure(uomConverter.convert(ingredient.getUom()));
        return ingredientCommand;
    }
}
