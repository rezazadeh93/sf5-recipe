package com.spring5.recipe.service;

import com.spring5.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findCommandByRecipeAndIngredientId(Long recipeId, Long ingredientId);
}