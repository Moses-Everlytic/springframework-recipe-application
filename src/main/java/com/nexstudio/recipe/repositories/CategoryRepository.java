package com.nexstudio.recipe.repositories;

import com.nexstudio.recipe.models.Category;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
