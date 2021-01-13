package com.spring5.recipe.service;

import com.spring5.recipe.commands.RecipeCommand;
import com.spring5.recipe.converters.RecipeCommandToRecipe;
import com.spring5.recipe.converters.RecipeToRecipeCommand;
import com.spring5.recipe.domain.Recipe;
import com.spring5.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RecipeServiceImplIT {
    public static final String TEST_DESCRIPTION = "test description";
    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Test
    @Transactional
    void saveRecipeCommand() {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe recipeTest = recipes.iterator().next();
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipeTest);

        //when
        recipeCommand.setDescription(TEST_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        //then
        assertEquals(TEST_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(recipeTest.getId(), savedRecipeCommand.getId());
        assertEquals(recipeTest.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(recipeTest.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}