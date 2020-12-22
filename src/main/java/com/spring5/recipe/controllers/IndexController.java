package com.spring5.recipe.controllers;

import com.spring5.recipe.domain.Category;
import com.spring5.recipe.domain.UnitOfMeasure;
import com.spring5.recipe.repositories.CategoryRepository;
import com.spring5.recipe.repositories.UnitOfMeasureRepository;
import com.spring5.recipe.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {
    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndex(Model model) {
        log.debug("getting index page here");

        model.addAttribute("recipes", recipeService.getList());

        return "index";
    }
}
