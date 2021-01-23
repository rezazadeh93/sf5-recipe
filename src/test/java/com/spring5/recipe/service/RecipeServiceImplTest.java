package com.spring5.recipe.service;

import com.spring5.recipe.converters.RecipeCommandToRecipe;
import com.spring5.recipe.converters.RecipeToRecipeCommand;
import com.spring5.recipe.domain.Recipe;
import com.spring5.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        recipeService = new RecipeServiceImpl(recipeCommandToRecipe, recipeToRecipeCommand, recipeRepository);
    }

    @Test
    void getRecipeByIdTest() {
        Long testValue = 1L;

        Recipe recipeTest = new Recipe();
        recipeTest.setId(testValue);

        when(recipeRepository.findById(testValue)).thenReturn(Optional.of(recipeTest));

        Recipe returnedRecipe = recipeService.findById(testValue);

        assertNotNull(returnedRecipe, "recipe returned is null");
        assertEquals(testValue, returnedRecipe.getId());

        verify(recipeRepository, times(1)).findById(testValue);
        verify(recipeRepository, never()).findAll();

    }

    @Test
    void getList() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.getList();

        assertEquals(1, recipes.size());

        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    void testDeleteById() {
        //given
        Long idToDelete = Long.valueOf(2L);
        recipeService.deleteById(idToDelete);

        //no when, because of delete method is a void function

        //then
        verify(recipeRepository, times(1)).deleteById(idToDelete);
    }
}