package com.spring5.recipe.service;

import com.spring5.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getList();

}
