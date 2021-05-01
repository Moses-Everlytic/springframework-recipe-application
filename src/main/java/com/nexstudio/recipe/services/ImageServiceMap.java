package com.nexstudio.recipe.services;

import java.io.IOException;
import java.util.Optional;

import com.nexstudio.recipe.models.Recipe;
import com.nexstudio.recipe.repositories.RecipeRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceMap implements ImageService {
    private final RecipeRepository recipeRepository;

    public ImageServiceMap(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    
    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();
            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }

            recipe.setImage(byteObjects);

            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("Error Occured ", e);

            e.printStackTrace();
        }

    }
    
}
