package com.spring5.recipe.service;

import com.spring5.recipe.commands.IngredientCommand;
import com.spring5.recipe.converters.IngredientToIngredientCommand;
import com.spring5.recipe.domain.Ingredient;
import com.spring5.recipe.domain.Recipe;
import com.spring5.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findCommandByRecipeAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            // @Todo Impl error 404 must handle
            log.error("Ingredient Service: Recipe ID Not Found!");
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();

        if (ingredientCommandOptional.isEmpty()) {
            // @Todo Impl error 404 must handle
            log.error("Ingredient Service: Ingredient ID Not Found!");
        }

        return ingredientCommandOptional.get();
    }
}
