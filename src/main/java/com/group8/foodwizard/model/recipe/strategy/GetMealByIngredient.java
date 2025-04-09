package com.group8.foodwizard.model.recipe.strategy;

import com.group8.foodwizard.model.recipe.Ingredient;
import com.group8.foodwizard.model.recipe.Meal;
import com.group8.foodwizard.model.recipe.RecipeModel.CachedMealFetcher;

import java.util.Set;

public class GetMealByIngredient extends GetMealStrategy {

    private final Set<Ingredient> ingredients;

    public GetMealByIngredient(Set<Ingredient> ingredients, CachedMealFetcher fetcher) {
        super(fetcher);
        this.ingredients = ingredients;
    }

    @Override
    public Set<Meal> getMeals() {
        return fetcher.getMealsByIngredient(ingredients);
    }
}