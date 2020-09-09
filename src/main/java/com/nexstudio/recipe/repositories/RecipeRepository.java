package com.nexstudio.recipe.repositories;

import com.nexstudio.recipe.models.Recipe;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {  
}
