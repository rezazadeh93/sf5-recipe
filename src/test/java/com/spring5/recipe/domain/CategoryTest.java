package com.spring5.recipe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long valueId = 4L;

        category.setId(valueId);
        assertEquals(valueId, category.getId());
    }

    @Test
    void getDescription() {
        String descTest = "This Description is for test!";

        category.setDescription(descTest);
        assertEquals(descTest, category.getDescription());
    }

    @Test
    void getRecipes() {

    }
}