package com.spring5.recipe.bootstrap;

import com.spring5.recipe.domain.*;
import com.spring5.recipe.repositories.CategoryRepository;
import com.spring5.recipe.repositories.RecipeRepository;
import com.spring5.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipe());
    }

    private List<Recipe> getRecipe() {
        List<Recipe> recipes = new ArrayList<>(2);

        Optional<UnitOfMeasure> uomPiece = unitOfMeasureRepository.findByDescription("Piece");
        if (uomPiece.isEmpty()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> uomTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        if (uomTeaspoon.isEmpty()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> uomTablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        if (uomTablespoon.isEmpty()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> uomCup = unitOfMeasureRepository.findByDescription("Cup");
        if (uomCup.isEmpty()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> uomPinch = unitOfMeasureRepository.findByDescription("Pinch");
        if (uomPinch.isEmpty()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> uomCloves = unitOfMeasureRepository.findByDescription("Cloves");
        if (uomCloves.isEmpty()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        UnitOfMeasure uomPieceUOM = uomPiece.get();
        UnitOfMeasure uomTeaspoonUOM = uomTeaspoon.get();
        UnitOfMeasure uomTablespoonUOM = uomTablespoon.get();
        UnitOfMeasure uomCupUOM = uomCup.get();
        UnitOfMeasure uomPinchUOM = uomPinch.get();
        UnitOfMeasure uomClovesUOM = uomCloves.get();

        Optional<Category> americanCat = categoryRepository.findByDescription("American");
        if (americanCat.isEmpty()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<Category> mexicanCat = categoryRepository.findByDescription("Mexican");
        if (mexicanCat.isEmpty()) {
            throw new RuntimeException("Expected UOM Not Found");
        }

        Category americanGet = americanCat.get();
        Category mexicanGet = mexicanCat.get();

        Recipe recipeChicken = new Recipe();
        recipeChicken.setDescription("Spicy Grilled Chicken Tacos Recipe");
        recipeChicken.setPrepTime(20);
        recipeChicken.setServings(4);
        recipeChicken.setCookTime(15);
        recipeChicken.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        recipeChicken.setSource("Simply Recipes");
        recipeChicken.setDifficulty(Difficulty.MODERATE);

        Notes noteChicken = new Notes(recipeChicken, "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "\n" +
                "\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "Spicy Grilled Chicken TacosThe ancho chiles I use in the marinade are named for their wide shape. They are large, have a deep reddish brown color when dried, and are mild in flavor with just a hint of heat. You can find ancho chile powder at any markets that sell Mexican ingredients, or online.\n" +
                "\n" +
                "I like to put all the toppings in little bowls on a big platter at the center of the table: avocados, radishes, tomatoes, red onions, wedges of lime, and a sour cream sauce. I add arugula, as well – this green isn’t traditional for tacos, but we always seem to have some in the fridge and I think it adds a nice green crunch to the tacos.\n" +
                "\n" +
                "Everyone can grab a warm tortilla from the pile and make their own tacos just they way they like them.\n" +
                "\n" +
                "You could also easily double or even triple this recipe for a larger party. A taco and a cold beer on a warm day? Now that’s living!");
        recipeChicken.setNotes(noteChicken);

        recipeChicken.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "Spicy Grilled Chicken Tacos\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");

        Ingredient ingredientChicken1 = new Ingredient("ancho chili powder", BigDecimal.valueOf(2), recipeChicken, uomPieceUOM);
        recipeChicken.getIngredients().add(ingredientChicken1);
        Ingredient ingredientChicken2 = new Ingredient("dried oregano", BigDecimal.valueOf(1), recipeChicken, uomTablespoonUOM);
        recipeChicken.getIngredients().add(ingredientChicken2);
        Ingredient ingredientChicken3 = new Ingredient("dried cumin", BigDecimal.valueOf(1), recipeChicken, uomTeaspoonUOM);
        recipeChicken.getIngredients().add(ingredientChicken3);
        Ingredient ingredientChicken4 = new Ingredient("sugar", BigDecimal.valueOf(1), recipeChicken, uomTeaspoonUOM);
        recipeChicken.getIngredients().add(ingredientChicken4);
        Ingredient ingredientChicken5 = new Ingredient("salt", BigDecimal.valueOf(.5), recipeChicken, uomTeaspoonUOM);
        recipeChicken.getIngredients().add(ingredientChicken5);
        Ingredient ingredientChicken6 = new Ingredient("garlic, finely chopped", BigDecimal.valueOf(1), recipeChicken, uomClovesUOM);
        recipeChicken.getIngredients().add(ingredientChicken6);
        Ingredient ingredientChicken7 = new Ingredient("finely grated orange zest", BigDecimal.valueOf(1), recipeChicken, uomTablespoonUOM);
        recipeChicken.getIngredients().add(ingredientChicken7);
        Ingredient ingredientChicken8 = new Ingredient("fresh-squeezed orange juice", BigDecimal.valueOf(3), recipeChicken, uomTablespoonUOM);
        recipeChicken.getIngredients().add(ingredientChicken8);
        Ingredient ingredientChicken9 = new Ingredient("olive oil", BigDecimal.valueOf(2), recipeChicken, uomTablespoonUOM);
        recipeChicken.getIngredients().add(ingredientChicken9);
        Ingredient ingredientChicken10 = new Ingredient("skinless, boneless chicken thighs", BigDecimal.valueOf(6), recipeChicken, uomPieceUOM);
        recipeChicken.getIngredients().add(ingredientChicken10);
        Ingredient ingredientChicken11 = new Ingredient("small corn tortillas", BigDecimal.valueOf(8), recipeChicken, uomPieceUOM);
        recipeChicken.getIngredients().add(ingredientChicken11);
        Ingredient ingredientChicken12 = new Ingredient("packed baby arugula", BigDecimal.valueOf(3), recipeChicken, uomCupUOM);
        recipeChicken.getIngredients().add(ingredientChicken12);
        Ingredient ingredientChicken13 = new Ingredient("medium ripe avocados, sliced", BigDecimal.valueOf(2), recipeChicken, uomPieceUOM);
        recipeChicken.getIngredients().add(ingredientChicken13);
        Ingredient ingredientChicken14 = new Ingredient("radishes, thinly sliced", BigDecimal.valueOf(4), recipeChicken, uomPieceUOM);
        recipeChicken.getIngredients().add(ingredientChicken14);
        Ingredient ingredientChicken15 = new Ingredient("cherry tomatoes, halved", BigDecimal.valueOf(1), recipeChicken, uomCupUOM);
        recipeChicken.getIngredients().add(ingredientChicken15);
        Ingredient ingredientChicken16 = new Ingredient("red onion, thinly sliced", BigDecimal.valueOf(.25), recipeChicken, uomPieceUOM);
        recipeChicken.getIngredients().add(ingredientChicken16);
        Ingredient ingredientChicken17 = new Ingredient("Roughly chopped cilantro", BigDecimal.valueOf(.25), recipeChicken, uomCupUOM);
        recipeChicken.getIngredients().add(ingredientChicken17);
        Ingredient ingredientChicken18 = new Ingredient("sour cream thinned with 1/4 cup milk", BigDecimal.valueOf(.5), recipeChicken, uomCupUOM);
        recipeChicken.getIngredients().add(ingredientChicken18);
        Ingredient ingredientChicken19 = new Ingredient("lime, cut into wedges", BigDecimal.valueOf(1), recipeChicken, uomPieceUOM);
        recipeChicken.getIngredients().add(ingredientChicken19);

        recipeChicken.getCategories().add(americanGet);

        recipes.add(recipeChicken);

        Recipe recipeGuacamole = new Recipe();
        recipeGuacamole.setDescription("Perfect Guacamole");
        recipeGuacamole.setPrepTime(10);
        recipeGuacamole.setServings(4);
        recipeGuacamole.setCookTime(5);
        recipeGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        recipeGuacamole.setSource("Simply Recipes");
        recipeGuacamole.setDifficulty(Difficulty.EASY);

        Notes noteGuacamole = new Notes(recipeGuacamole, "The word “guacamole”, and the dip, are both originally from Mexico, where avocados have been cultivated for thousands of years. The name is derived from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce).");
        recipeGuacamole.setNotes(noteGuacamole);

        recipeGuacamole.setDirections(
                "1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl." +
                        "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                        "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n\n" +
                        "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n\n" +
                        "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n\n" +
                        "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving." +
                        "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve."
        );

        Ingredient ingredientGuacamole1 = new Ingredient("ripe avocados", BigDecimal.valueOf(2), recipeGuacamole, uomPieceUOM);
        recipeGuacamole.getIngredients().add(ingredientGuacamole1);
        Ingredient ingredientGuacamole2 = new Ingredient("salt, more to taste", BigDecimal.valueOf(.25), recipeGuacamole, uomTeaspoonUOM);
        recipeGuacamole.getIngredients().add(ingredientGuacamole2);
        Ingredient ingredientGuacamole3 = new Ingredient("fresh lime juice or lemon juice", BigDecimal.valueOf(1), recipeGuacamole, uomTablespoonUOM);
        recipeGuacamole.getIngredients().add(ingredientGuacamole3);
        Ingredient ingredientGuacamole4 = new Ingredient("minced red onion or thinly sliced green onion", BigDecimal.valueOf(.25), recipeGuacamole, uomCupUOM);
        recipeGuacamole.getIngredients().add(ingredientGuacamole4);
        Ingredient ingredientGuacamole5 = new Ingredient("serrano chiles, stems and seeds removed, minced", BigDecimal.valueOf(2), recipeGuacamole, uomPieceUOM);
        recipeGuacamole.getIngredients().add(ingredientGuacamole5);
        Ingredient ingredientGuacamole6 = new Ingredient("cilantro (leaves and tender stems), finely chopped", BigDecimal.valueOf(2), recipeGuacamole, uomTablespoonUOM);
        recipeGuacamole.getIngredients().add(ingredientGuacamole6);
        Ingredient ingredientGuacamole7 = new Ingredient("freshly grated black pepper", BigDecimal.valueOf(1), recipeGuacamole, uomPinchUOM);
        recipeGuacamole.getIngredients().add(ingredientGuacamole7);
        Ingredient ingredientGuacamole8 = new Ingredient("ripe tomato, seeds and pulp removed, chopped", BigDecimal.valueOf(.5), recipeGuacamole, uomPieceUOM);
        recipeGuacamole.getIngredients().add(ingredientGuacamole8);
        Ingredient ingredientGuacamole9 = new Ingredient("Red radishes or jicama, to garnish", BigDecimal.valueOf(5), recipeGuacamole, uomPieceUOM);
        recipeGuacamole.getIngredients().add(ingredientGuacamole9);
        Ingredient ingredientGuacamole10 = new Ingredient("Tortilla chips, to serve", BigDecimal.valueOf(2), recipeGuacamole, uomPieceUOM);
        recipeGuacamole.getIngredients().add(ingredientGuacamole10);

        recipeGuacamole.getCategories().add(americanGet);
        recipeGuacamole.getCategories().add(mexicanGet);

        recipes.add(recipeGuacamole);

        return recipes;
    }
}
