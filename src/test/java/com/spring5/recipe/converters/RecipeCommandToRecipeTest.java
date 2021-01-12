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
    public static final  Long ID_VALUE = 1L;
    public static final  String DESCRIPTION = "recipe description is here";
    public static final Integer PREP_TIME = 15;
    public static final Integer COOK_TIME = 20;
    public static final Integer SERVINGS = 4;
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
                new NotesCommandToNotes(),
                new CategoryCommandToCategory());
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    void convert() {
        //given
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

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTE_ID);
        source.setNotes(notesCommand);

        IngredientCommand ingredientTemp1 = new IngredientCommand();
        ingredientTemp1.setId(INGREDIENT_ID);
        IngredientCommand ingredientTemp2 = new IngredientCommand();
        ingredientTemp1.setDescription(INGREDIENT_DESC);

        Set<IngredientCommand> ingredientCommands = new HashSet<>();
        ingredientCommands.add(ingredientTemp1);
        ingredientCommands.add(ingredientTemp2);
        source.setIngredients(ingredientCommands);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CATEGORY_ID);
        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setDescription(CATEGORY_DESC);

        source.getCategories().add(categoryCommand1);
        source.getCategories().add(categoryCommand2);


        //when
        Recipe recipeSaved = converter.convert(source);

        // then
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