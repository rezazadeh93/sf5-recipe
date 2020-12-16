package com.spring5.recipe.controllers;

import com.spring5.recipe.domain.Category;
import com.spring5.recipe.domain.UnitOfMeasure;
import com.spring5.recipe.repositories.CategoryRepository;
import com.spring5.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndex() {
        Optional<Category> optionalCategory = categoryRepository.findByDescription("Mexican");
        Optional<UnitOfMeasure> optionalUnitOfMeasure = unitOfMeasureRepository.findByDescription("Tablespoon");

        System.out.println(optionalCategory.get().getId());
        System.out.println(optionalUnitOfMeasure.get().getId());

        return "index";
    }
}
