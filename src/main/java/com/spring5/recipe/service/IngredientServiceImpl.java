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
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findCommandByRecipeAndIngredientId(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            // @Todo Impl error 404 must handle
            log.error("Ingredient Service: Recipe ID Not Found!");
        }

        Recipe recipe = recipeOptional.get();

        Ingredient ingredientCommandOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst().orElse(null);

        if (ingredientCommandOptional == null) {
            // @Todo Impl error 404 must handle
            log.error("Ingredient Service: Ingredient ID Not Found!");
        }
        IngredientCommand command = ingredientToIngredientCommand.convert(ingredientCommandOptional);
        return command;
    }
}
