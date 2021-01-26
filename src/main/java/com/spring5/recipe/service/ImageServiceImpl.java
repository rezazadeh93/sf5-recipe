package com.spring5.recipe.service;

import com.spring5.recipe.domain.Recipe;
import com.spring5.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveNewImageFile(Long recipeId, MultipartFile file) {
        log.debug("Image Service @SaveImageFile => Receiving an Image");

        try {
            Recipe recipe = recipeRepository.findById(recipeId).get();

            Byte[] bytesObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                bytesObjects[i++] = b;
            }

            recipe.setImage(bytesObjects);
            recipeRepository.save(recipe);

        } catch (IOException e) {
            // @Todo returning an error to view
            log.error("Image Service @SaveImageFile => " + e);

            e.printStackTrace();
        }
    }
}
