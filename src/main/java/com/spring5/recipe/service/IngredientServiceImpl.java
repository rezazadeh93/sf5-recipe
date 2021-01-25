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

        Ingredient ingredientFound = recipe.findIngredientByObj(ingredientCommand);

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
        // check by description that isn't unique property,
        // it's not totally safe, but it's best guess
        Ingredient returnedIngredient = savedRecipe.findIngredientByObj(ingredientCommand);

        return ingredientToIngredientCommand.convert(returnedIngredient);
    }

    @Override
    public void deleteById(Long recipeId, Long id) {
        log.debug("ingredient service @delete => deleting ingredient id: " + id);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            log.debug("ingredient service @delete => Found Recipe: ");

            Recipe recipe = recipeOptional.get();

            recipe.getIngredients().removeIf(ingredient -> {
                if (ingredient.getId().equals(id)) {
                    log.debug("ingredient service @delete => Found Ingredient");
                    ingredient.setRecipe(null);
                    return true;
                }

                return false;
            });

            recipeRepository.save(recipe);
        } else {

            // @Todo return 404 error
            log.error("ingredient service @delete => recipe id not found, id: " + recipeId);
        }
    }
}
