package com.spring5.recipe.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ImageService {
    void saveNewImageFile(Long recipeId, MultipartFile file);
}
