package com.spring5.recipe.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.naming.Name;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Data
@EqualsAndHashCode(exclude = {"categories", "notes", "ingredients"})
@Entity
@ToString(exclude = {"categories", "notes", "ingredients"})
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        //  this relationship is bi-directional
        if (notes != null) {
            this.notes = notes;

            if (notes.getRecipe() == null) {
//                notes.setRecipe(this);
            }
        }
    }

    public Recipe addIngredient(Ingredient ingredient) {
        //  this relationship is bi-directional
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);

        return this;
    }

    public Ingredient findIngredientById(Long id) {
        Set<Ingredient> ingredientSet = getIngredients();

        for (Ingredient ingredient: ingredientSet) {
            if (ingredient.getId().equals(id)) {
                return ingredient;
            }
        }

        log.error("Recipe Domain @findIngredientById => Ingredient Not Found: " + id);

        return null;
    }

}
