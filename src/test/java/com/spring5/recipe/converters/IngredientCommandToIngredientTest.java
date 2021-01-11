package com.spring5.recipe.converters;

import com.spring5.recipe.commands.IngredientCommand;
import com.spring5.recipe.commands.UnitOfMeasureCommand;
import com.spring5.recipe.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {
    private final static Long ID_VALUE = 1L;
    private final static String DESCRIPTION = "cheese";
    private final static BigDecimal BIG_DECIMAL = BigDecimal.valueOf(1L);
    private final static Long UOM_ID = 2L;
    private final static String UOM_DESC = "Unit of measure description";

    IngredientCommandToIngredient converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void nullableParameterTest() {
        assertNull(converter.convert(null));
    }

    @Test
    void notNullTest() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    void convert() {
        IngredientCommand source = new IngredientCommand();
        source.setId(ID_VALUE);
        source.setDescription(DESCRIPTION);
        source.setAmount(BIG_DECIMAL);

        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(UOM_ID);
        uomCommand.setDescription(UOM_DESC);
        source.setUom(uomCommand);

        Ingredient ingredient = converter.convert(source);

        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(BIG_DECIMAL, ingredient.getAmount());

        assertEquals(UOM_ID, ingredient.getUom().getId());
        assertEquals(UOM_DESC, ingredient.getUom().getDescription());
    }
}