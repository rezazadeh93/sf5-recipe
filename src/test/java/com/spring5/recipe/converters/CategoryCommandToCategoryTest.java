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
    void nullableTest() {
        assertNull(converter.convert(null));
    }

    @Test
    void notNullTest() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        CategoryCommand source = new CategoryCommand();
        source.setId(ID_VALUE);
        source.setDescription(DESCRIPTION);

        Category saved = converter.convert(source);

        assertNotNull(saved);
        assertEquals(ID_VALUE, saved.getId());
        assertEquals(DESCRIPTION, saved.getDescription());

    }
}