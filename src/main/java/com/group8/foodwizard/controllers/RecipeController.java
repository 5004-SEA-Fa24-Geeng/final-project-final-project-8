package com.group8.foodwizard.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.group8.foodwizard.model.recipe.Ingredient;
import com.group8.foodwizard.model.recipe.Meal;
import com.group8.foodwizard.model.recipe.Recipe;
import com.group8.foodwizard.model.recipe.RecipeModel;
import com.group8.foodwizard.model.requests.RecipeRequest;

/**
 * Controller for handling recipe-related API endpoints.
 */
@RestController
@CrossOrigin(origins = { "http://localhost:5173", "https://foodwizard.fly.dev" }, methods = { RequestMethod.GET,
        RequestMethod.POST })
public class RecipeController {

    /**
     * Retrieves all available ingredients from the recipe model.
     *
     * @return a set of Ingredient objects, or an empty set if an error occurs
     */
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

    /**
     * Retrieves all available recipe categories from the recipe model.
     *
     * @return a set of category names as strings, or an empty set if an error
     *         occurs
     */
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

    /**
     * Retrieves all available recipe regions (areas) from the recipe model.
     *
     * @return a set of region names as strings, or an empty set if an error occurs
     */
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

    /**
     * Retrieves recipe previews (meals) that match the given filter criteria.
     *
     * @param recipeRequest the request object containing ingredients, category, and
     *                      region
     * @return a set of Meal objects that match the filters, or an empty set if an
     *         error occurs
     */
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

    /**
     * Retrieves the full recipe for a given meal ID.
     *
     * @param mealId the ID of the meal to retrieve the recipe for
     * @return the Recipe object corresponding to the meal ID, or null if an error
     *         occurs
     */
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
