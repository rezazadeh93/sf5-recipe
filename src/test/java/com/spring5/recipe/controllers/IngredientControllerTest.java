package com.spring5.recipe.controllers;

import com.spring5.recipe.commands.IngredientCommand;
import com.spring5.recipe.commands.RecipeCommand;
import com.spring5.recipe.repositories.RecipeRepository;
import com.spring5.recipe.service.IngredientService;
import com.spring5.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {
    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @InjectMocks
    IngredientController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new IngredientController(recipeService, ingredientService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listIngredientsTest() throws Exception {
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        //then
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    void testShowRecipeIngredient() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();

        //when
        when(ingredientService.findCommandByRecipeAndIngredientId(anyLong(), anyLong())).thenReturn(command);

        //then
        mockMvc.perform(get("/recipe/3/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }
}