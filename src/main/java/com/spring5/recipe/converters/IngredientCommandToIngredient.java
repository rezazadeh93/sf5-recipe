package com.spring5.recipe.converters;

import com.spring5.recipe.commands.IngredientCommand;
import com.spring5.recipe.commands.UnitOfMeasureCommand;
import com.spring5.recipe.domain.Ingredient;
import com.spring5.recipe.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        Recipe recipe = new Recipe();
        recipe.setId(source.getRecipeId());
        ingredient.setRecipe(recipe);
        ingredient.setDescription(source.getDescription());
        ingredient.setAmount(source.getAmount());
        ingredient.setUom(uomConverter.convert(source.getUom()));
        return ingredient;
    }
}
