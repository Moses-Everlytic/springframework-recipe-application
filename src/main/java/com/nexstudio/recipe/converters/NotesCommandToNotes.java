package com.nexstudio.recipe.converters;

import com.nexstudio.recipe.commands.NotesCommand;
import com.nexstudio.recipe.models.Notes;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import lombok.Synchronized;

public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if(source == null) {
            return null;
        }

        final Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());
        return notes;
    }
}
