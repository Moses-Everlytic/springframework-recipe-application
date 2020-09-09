package com.nexstudio.recipe.repositories;

import java.util.Optional;

import com.nexstudio.recipe.models.Category;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}
