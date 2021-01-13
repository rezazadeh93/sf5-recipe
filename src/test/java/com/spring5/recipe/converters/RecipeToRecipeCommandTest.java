package com.spring5.recipe.converters;

import com.spring5.recipe.commands.CategoryCommand;
import com.spring5.recipe.commands.IngredientCommand;
import com.spring5.recipe.commands.RecipeCommand;
import com.spring5.recipe.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {
    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "recipe description is here";
    public static final Integer PREP_TIME = 15;
    public static final Integer COOK_TIME = 20;
    public static final Integer SERVINGS = 4;
    public static final String EXAMPLE = "example";
    public static final String URL_VALUE = "http://www.example.com";
    public static final String DIRECTION = "this is just test direction, this is just test direction, this is just test direction";
    public static final Difficulty DIFFICULTY = Difficulty.HARD;
    public static final Long CATEGORY_ID1 = 6L;
    public static final Long CATEGORY_ID2 = 7L;
    public static final Long NOTE_ID = 5L;
    public static final Long INGREDIENT_ID1 = 3L;
    public static final Long INGREDIENT_ID2 = 6L;

    RecipeToRecipeCommand converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeCommand(
                new NotesToNotesCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand());
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    void convert() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(ID_VALUE);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(EXAMPLE);
        recipe.setUrl(URL_VALUE);
        recipe.setDirections(DIRECTION);
        recipe.setDifficulty(DIFFICULTY);

        Notes notes = new Notes();
        notes.setId(NOTE_ID);
        recipe.setNotes(notes);

        Ingredient ingredientTemp1 = new Ingredient();
        ingredientTemp1.setId(INGREDIENT_ID1);
        Ingredient ingredientTemp2 = new Ingredient();
        ingredientTemp2.setId(INGREDIENT_ID2);

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredientTemp1);
        ingredients.add(ingredientTemp2);
        recipe.setIngredients(ingredients);

        Category category1 = new Category();
        category1.setId(CATEGORY_ID1);
        Category category2 = new Category();
        category2.setId(CATEGORY_ID2);

        recipe.getCategories().add(category1);
        recipe.getCategories().add(category2);

        //when
        RecipeCommand recipeCommand = converter.convert(recipe);

        // then
        assertNotNull(recipeCommand);
        assertNotNull(recipeCommand.getIngredients());
        assertNotNull(recipeCommand.getNotes());
        assertNotNull(recipeCommand.getCategories());
        assertEquals(ID_VALUE, recipeCommand.getId());
        assertEquals(DESCRIPTION, recipeCommand.getDescription());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(EXAMPLE, recipeCommand.getSource());
        assertEquals(URL_VALUE, recipeCommand.getUrl());
        assertEquals(DIRECTION, recipeCommand.getDirections());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());

        assertEquals(NOTE_ID, recipeCommand.getNotes().getId());
        assertEquals(2, recipeCommand.getIngredients().size());
        assertEquals(2, recipeCommand.getCategories().size());
    }
}