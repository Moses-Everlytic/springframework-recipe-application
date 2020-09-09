package com.nexstudio.recipe.repositories;

import java.util.Optional;

import com.nexstudio.recipe.models.UnitOfMeasure;

import org.springframework.data.repository.CrudRepository;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> { 

    Optional<UnitOfMeasure> findByDescription(String desciption);
}
