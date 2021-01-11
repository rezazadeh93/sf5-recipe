package com.spring5.recipe.converters;

import com.spring5.recipe.commands.UnitOfMeasureCommand;
import com.spring5.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {
    private final static Long ID_VALUE = 1L;
    private final static String DESCRIPTION = "description";

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }
    
    @Test
    void nullParameterTest() {
        assertNull(converter.convert(null));
    }

    @Test
    void emptyObjectTest() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    void convert() {
        UnitOfMeasureCommand source = new UnitOfMeasureCommand();
        source.setId(ID_VALUE);
        source.setDescription(DESCRIPTION);

        UnitOfMeasure uom = converter.convert(source);

        assertNotNull(uom);
        assertEquals(ID_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}