package com.spring5.recipe.service;

import com.spring5.recipe.commands.RecipeCommand;
import com.spring5.recipe.converters.RecipeCommandToRecipe;
import com.spring5.recipe.converters.RecipeToRecipeCommand;
import com.spring5.recipe.domain.Recipe;
import com.spring5.recipe.exception.NotFoundException;
import com.spring5.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand,
                             RecipeRepository recipeRepository) {
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getList() {
        log.debug("logging service: @getList method");

        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long Id) {
        Optional<Recipe> recipe = recipeRepository.findById(Id);

        if (recipe.isEmpty()) {
            // throw new RuntimeException("Recipe Not Found!");
            throw new NotFoundException("Recipe Not Found, For ID value: " + Id.toString());
        }

        return recipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe recipe = recipeRepository.save(detachedRecipe);
        log.debug("logging service: @saveRecipeCommand => Recipe ID : " + recipe.getId());
        return recipeToRecipeCommand.convert(recipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
