package com.spring5.recipe.service;

import com.spring5.recipe.commands.IngredientCommand;
import com.spring5.recipe.converters.IngredientToIngredientCommand;
import com.spring5.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring5.recipe.domain.Ingredient;
import com.spring5.recipe.domain.Recipe;
import com.spring5.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {
    public static final long RECIPE_ID = 1L;
    public static final long INGREDIENT_ID = 2L;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    @Mock
    RecipeRepository recipeRepository;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, recipeRepository);
    }

    @Test
    void findCommandByRecipeAndIngredientId() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        Ingredient ingredientTemp1 = new Ingredient();
        ingredientTemp1.setId(1L);
        Ingredient ingredientTemp2 = new Ingredient();
        ingredientTemp2.setId(INGREDIENT_ID);
        Ingredient ingredientTemp3 = new Ingredient();
        ingredientTemp3.setId(3L);

        recipe.addIngredient(ingredientTemp1);
        recipe.addIngredient(ingredientTemp2);
        recipe.addIngredient(ingredientTemp3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(RECIPE_ID)).thenReturn(recipeOptional);

        //when
        IngredientCommand command = ingredientService.findCommandByRecipeAndIngredientId(RECIPE_ID, INGREDIENT_ID);

        //then
        assertNotNull(command);
        assertEquals(RECIPE_ID, command.getRecipeId());
        assertEquals(INGREDIENT_ID, command.getId());
        verify(recipeRepository, times(1)).findById(RECIPE_ID);
    }
}