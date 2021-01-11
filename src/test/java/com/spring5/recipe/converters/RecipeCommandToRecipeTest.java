package com.spring5.recipe.converters;

import com.spring5.recipe.commands.*;
import com.spring5.recipe.domain.Difficulty;
import com.spring5.recipe.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {
    private final static Long ID_VALUE = 1L;
    private final static String DESCRIPTION = "recipe description is here";
    public static final int PREP_TIME = 15;
    public static final int COOK_TIME = 20;
    public static final int SERVINGS = 4;
    public static final String EXAMPLE = "example";
    public static final String URL_VALUE = "http://www.example.com";
    public static final String DIRECTION = "this is just test direction, this is just test direction, this is just test direction";
    public static final Difficulty DIFFICULTY = Difficulty.HARD;
    public static final String CATEGORY_DESC = "iranian";
    public static final long CATEGORY_ID = 6L;
    public static final long NOTE_ID = 5L;
    public static final long INGREDIENT_ID = 3L;
    public static final String INGREDIENT_DESC = "milk";

    RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes(), new CategoryCommandToCategory());
    }

    @Test
    void nullableTest() {
        assertNull(converter.convert(null));
    }

    @Test
    void notNullTest() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    void convert() {
        RecipeCommand source = new RecipeCommand();
        source.setId(ID_VALUE);
        source.setDescription(DESCRIPTION);
        source.setPrepTime(PREP_TIME);
        source.setCookTime(COOK_TIME);
        source.setServings(SERVINGS);
        source.setSource(EXAMPLE);
        source.setUrl(URL_VALUE);
        source.setDirections(DIRECTION);
        source.setDifficulty(DIFFICULTY);

        IngredientCommand ingredientTemp = new IngredientCommand();
        ingredientTemp.setId(INGREDIENT_ID);
        ingredientTemp.setUom(new UnitOfMeasureCommand());

        IngredientCommand ingredientTemp1 = new IngredientCommand();
        ingredientTemp.setDescription(INGREDIENT_DESC);
        ingredientTemp.setUom(new UnitOfMeasureCommand());

        Set<IngredientCommand> ingredientCommands = new HashSet<>();
        ingredientCommands.add(ingredientTemp);
        ingredientCommands.add(ingredientTemp1);
        source.setIngredients(ingredientCommands);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTE_ID);
        source.setNotes(notesCommand);

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(CATEGORY_ID);

        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand.setDescription(CATEGORY_DESC);

        Set<CategoryCommand> categoryCommandSet = new HashSet<>();
        categoryCommandSet.add(categoryCommand);
        categoryCommandSet.add(categoryCommand2);

        source.setCategories(categoryCommandSet);

        Recipe recipeSaved = converter.convert(source);

        assertNotNull(recipeSaved);
        assertNotNull(recipeSaved.getIngredients());
        assertNotNull(recipeSaved.getNotes());
        assertNotNull(recipeSaved.getCategories());
        assertEquals(ID_VALUE, recipeSaved.getId());
        assertEquals(DESCRIPTION, recipeSaved.getDescription());
        assertEquals(PREP_TIME, recipeSaved.getPrepTime());
        assertEquals(COOK_TIME, recipeSaved.getCookTime());
        assertEquals(SERVINGS, recipeSaved.getServings());
        assertEquals(EXAMPLE, recipeSaved.getSource());
        assertEquals(URL_VALUE, recipeSaved.getUrl());
        assertEquals(DIRECTION, recipeSaved.getDirections());
        assertEquals(DIFFICULTY, recipeSaved.getDifficulty());

        assertEquals(2, recipeSaved.getIngredients().size());
        assertEquals(NOTE_ID, recipeSaved.getNotes().getId());
        assertEquals(2, recipeSaved.getCategories().size());
    }
}