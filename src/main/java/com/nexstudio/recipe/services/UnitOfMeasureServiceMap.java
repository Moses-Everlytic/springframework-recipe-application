package com.nexstudio.recipe.services;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.nexstudio.recipe.commands.UnitOfMeasureCommand;
import com.nexstudio.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.nexstudio.recipe.repositories.UnitOfMeasureRepository;

import org.springframework.stereotype.Service;

@Service
public class UnitOfMeasureServiceMap implements UnitOfMeasureService {
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceMap(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                            .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                            .collect(Collectors.toSet());
    }
    
}
