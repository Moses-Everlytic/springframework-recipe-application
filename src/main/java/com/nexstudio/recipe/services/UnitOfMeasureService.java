package com.nexstudio.recipe.services;

import java.util.Set;

import com.nexstudio.recipe.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUoms();
}
