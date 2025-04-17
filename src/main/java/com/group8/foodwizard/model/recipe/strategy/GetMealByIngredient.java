package com.group8.foodwizard.model.recipe.strategy;

import com.group8.foodwizard.model.recipe.Ingredient;
import com.group8.foodwizard.model.recipe.Meal;
import com.group8.foodwizard.model.recipe.RecipeModel.CachedMealFetcher;

import java.util.Set;

public class GetMealByIngredient extends GetMealStrategy {
    /** The ingredients that the user selected. */
    private final Set<Ingredient> ingredients;

    /**  Constructs a GetMealByIngredients with the given ingredients and cached fetcher.
     *
     * @param ingredients the ingredients that the user selected
     * @param fetcher the fetcher to fetch the cached data
     * */
    public GetMealByIngredient(Set<Ingredient> ingredients, CachedMealFetcher fetcher) {
        super(fetcher);
        this.ingredients = ingredients;
    }

    @Override
    public Set<Meal> getMeals() {
        return getFetcher().getMealsByIngredient(ingredients);
    }
}
