package com.nexstudio.recipe.services;

import java.util.Set;

import com.nexstudio.recipe.models.Recipe;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long l);
}
