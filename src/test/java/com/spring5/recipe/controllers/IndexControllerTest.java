package com.spring5.recipe.controllers;

import com.spring5.recipe.domain.Recipe;
import com.spring5.recipe.repositories.RecipeRepository;
import com.spring5.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IndexControllerTest {
    IndexController indexController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        indexController = new IndexController(recipeService);
    }

    @Test
    void getIndex() {
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(new Recipe());

        String verifyString = indexController.getIndex(model);

        assertEquals("index", verifyString);
        verify(recipeService, times(1)).getList();
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
    }
}