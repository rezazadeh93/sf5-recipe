package com.spring5.recipe.service;

import com.spring5.recipe.commands.IngredientCommand;
import com.spring5.recipe.converters.IngredientCommandToIngredient;
import com.spring5.recipe.converters.IngredientToIngredientCommand;
import com.spring5.recipe.domain.Ingredient;
import com.spring5.recipe.domain.Recipe;
import com.spring5.recipe.repositories.RecipeRepository;
import com.spring5.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository uomRepository;

    public IngredientServiceImpl(IngredientCommandToIngredient ingredientCommandToIngredient,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 RecipeRepository recipeRepository, UnitOfMeasureRepository uomRepository) {
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.uomRepository = uomRepository;
    }

    @Override
    public IngredientCommand findCommandByRecipeAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isEmpty()) {
            // @Todo Impl error 404 must handle
            log.error("Ingredient Service: Recipe ID Not Found!");

            return new IngredientCommand();
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

            return new IngredientCommand();
        }

        return ingredientCommandOptional.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (recipeOpt.isEmpty()) {
            // @Todo Not Found error must handle properly
            log.error("Ingredient service @saveIngredient => Recipe ID Not Found: " + ingredientCommand.getId());
            return new IngredientCommand();
        }

        Recipe recipe = recipeOpt.get();

        Ingredient ingredientFound = recipe.findIngredientById(ingredientCommand.getId());

        if (ingredientFound != null) {

            ingredientFound.setDescription(ingredientCommand.getDescription());
            ingredientFound.setAmount(ingredientCommand.getAmount());
            ingredientFound.setUom(uomRepository
                    .findById(ingredientCommand.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("UOM Not Found")));
        } else {
            // add new ingredient
            recipe.addIngredient(Objects.requireNonNull(ingredientCommandToIngredient.convert(ingredientCommand)));
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        // @Todo check for fail
        Ingredient returnedIngredient = savedRecipe.findIngredientById(ingredientCommand.getId());

        return ingredientToIngredientCommand.convert(returnedIngredient);
    }
}
