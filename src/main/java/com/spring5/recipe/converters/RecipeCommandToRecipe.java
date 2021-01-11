package com.spring5.recipe.converters;

import com.spring5.recipe.commands.CategoryCommand;
import com.spring5.recipe.commands.IngredientCommand;
import com.spring5.recipe.commands.RecipeCommand;
import com.spring5.recipe.domain.Category;
import com.spring5.recipe.domain.Ingredient;
import com.spring5.recipe.domain.Notes;
import com.spring5.recipe.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesCommandToNotes notesCommandToNotes;
    private final CategoryCommandToCategory categoryCommandToCategory;

    public RecipeCommandToRecipe(IngredientCommandToIngredient ingredientCommandToIngredient,
                                 NotesCommandToNotes notesCommandToNotes,
                                 CategoryCommandToCategory categoryCommandToCategory) {
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.notesCommandToNotes = notesCommandToNotes;
        this.categoryCommandToCategory = categoryCommandToCategory;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setDirections(source.getDirections());

        Set<Ingredient> ingredients = new HashSet<>();
        for (IngredientCommand ingredient: source.getIngredients()) {
            ingredients.add(ingredientCommandToIngredient.convert(ingredient));
        }

        recipe.setIngredients(ingredients);

        recipe.setDifficulty(source.getDifficulty());

        Notes notes = notesCommandToNotes.convert(source.getNotes());
        if (notes == null) {
            notes = new Notes();
        }

        recipe.setNotes(notes);

        Set<Category> categories = new HashSet<>();
        for (CategoryCommand category: source.getCategories()) {
            categories.add(categoryCommandToCategory.convert(category));
        }

        recipe.setCategories(categories);

        return recipe;
    }
}
