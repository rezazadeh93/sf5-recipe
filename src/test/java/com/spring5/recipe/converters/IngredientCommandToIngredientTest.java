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
    private final static BigDecimal AMOUNT = BigDecimal.valueOf(1L);
    private final static Long UOM_ID = 2L;
    private final static String UOM_DESC = "Unit of measure description";

    IngredientCommandToIngredient converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    void convert() {
        //given
        IngredientCommand source = new IngredientCommand();
        source.setId(ID_VALUE);
        source.setDescription(DESCRIPTION);
        source.setAmount(AMOUNT);

        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(UOM_ID);
        uomCommand.setDescription(UOM_DESC);
        source.setUom(uomCommand);

        //when
        Ingredient ingredient = converter.convert(source);

        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());

        assertEquals(UOM_ID, ingredient.getUom().getId());
        assertEquals(UOM_DESC, ingredient.getUom().getDescription());
    }

    @Test
    void convertWithNullUOM() {
        //given
        IngredientCommand source = new IngredientCommand();
        source.setId(ID_VALUE);
        source.setDescription(DESCRIPTION);
        source.setAmount(AMOUNT);

        //when
        Ingredient ingredient = converter.convert(source);

        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(AMOUNT, ingredient.getAmount());

    }

}