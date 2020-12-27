package com.spring5.recipe.repositories;

import com.spring5.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {
    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    void findByDescriptionTsp() {
        Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", uom.get().getDescription());
    }

    @Test
    void findByDescriptionCup() {
        Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByDescription("Cup");

        assertEquals("Cup", uom.get().getDescription());
    }
}