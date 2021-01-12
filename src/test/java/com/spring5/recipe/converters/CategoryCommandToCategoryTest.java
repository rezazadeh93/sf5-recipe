package com.spring5.recipe.converters;

import com.spring5.recipe.commands.CategoryCommand;
import com.spring5.recipe.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {
    private final static Long ID_VALUE = 1L;
    private final static String DESCRIPTION = "european";

    CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        //given
        CategoryCommand source = new CategoryCommand();
        source.setId(ID_VALUE);
        source.setDescription(DESCRIPTION);

        //when
        Category saved = converter.convert(source);

        //then
        assertEquals(ID_VALUE, saved.getId());
        assertEquals(DESCRIPTION, saved.getDescription());
    }

}