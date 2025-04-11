package com.group8.foodwizard.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group8.foodwizard.model.recipe.Ingredient;
import com.group8.foodwizard.model.recipe.Meal;
import com.group8.foodwizard.model.recipe.Recipe;
import com.group8.foodwizard.model.recipe.RecipeModel;
import com.group8.foodwizard.model.requests.RecipeRequest;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class RecipeController {

    @GetMapping("/api/ingredients")
    public Set<Ingredient> getAllIngredients() {
        try {
            RecipeModel model = RecipeModel.getInstance();
            return model.getAllIngredients();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    @GetMapping("/api/categories")
    public Set<String> getAllCategories() {
        try {
            RecipeModel model = RecipeModel.getInstance();
            return model.getAllCategories();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    @GetMapping("/api/regions")
    public Set<String> getAllRegions() {
        try {
            RecipeModel model = RecipeModel.getInstance();
            return model.getAllAreas();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    @PostMapping("/api/getRecipePreviews")
    public Set<Meal> getRecipePreviews(@RequestBody RecipeRequest recipeRequest) {
        try {
            RecipeModel model = RecipeModel.getInstance();
            return model.processMeals(
                    recipeRequest.getIngredients(),
                    recipeRequest.getCategory(),
                    recipeRequest.getRegion());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    @GetMapping("/api/recipe/{mealId}")
    public Recipe getRecipeByIdMeal(@PathVariable int mealId) {
        try {
            RecipeModel model = RecipeModel.getInstance();
            return model.getRecipeByIdMeal(mealId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}