package com.spring5.recipe.service;

import com.spring5.recipe.commands.IngredientCommand;
import com.spring5.recipe.converters.IngredientCommandToIngredient;
import com.spring5.recipe.converters.IngredientToIngredientCommand;
import com.spring5.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.spring5.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.spring5.recipe.domain.Ingredient;
import com.spring5.recipe.domain.Recipe;
import com.spring5.recipe.repositories.RecipeRepository;
import com.spring5.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {
    public static final long RECIPE_ID = 1L;
    public static final long INGREDIENT_ID = 2L;
    public static final String TEST_DESCRIPTION = "test description";
    public static final BigDecimal TEST_AMOUNT = BigDecimal.valueOf(23.34);

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository uomRepository;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientCommandToIngredient, ingredientToIngredientCommand,
                recipeRepository, uomRepository);
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

    @Test
    void testSaveNewIngredientCommand() {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(RECIPE_ID);
        ingredientCommand.setId(INGREDIENT_ID);

        Recipe saveRecipe = new Recipe();
        saveRecipe.setId(RECIPE_ID);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);

        saveRecipe.addIngredient(ingredient);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
        when(recipeRepository.save(any())).thenReturn(saveRecipe);

        //when
        IngredientCommand returnedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //then
        assertEquals(INGREDIENT_ID, returnedCommand.getId());
        assertEquals(RECIPE_ID, returnedCommand.getRecipeId());

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testDeleteIngredientById() {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        ingredient1.setRecipe(recipe);
        ingredient2.setRecipe(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        // when
        ingredientService.deleteById(2L, 2L);

        //then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}