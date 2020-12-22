package com.spring5.recipe.domain;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNote;

    public Notes() {
    }

    public Notes(String recipeNote) {
        this.recipeNote = recipeNote;
    }
}
