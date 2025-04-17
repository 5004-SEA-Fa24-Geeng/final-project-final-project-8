package com.group8.foodwizard.model.recipe.strategy;

import com.group8.foodwizard.model.recipe.Meal;
import com.group8.foodwizard.model.recipe.RecipeModel.CachedMealFetcher;

import java.util.Set;

public class GetMealByCategory extends GetMealStrategy {
    /** The category that the user selected. */
    private final String category;

    /**  Constructs a GetMealByCategory with the given category and cached fetcher.
     *
     * @param category the category that the user selected
     * @param fetcher the fetcher to fetch the cached data
     * */
    public GetMealByCategory(String category, CachedMealFetcher fetcher) {
        super(fetcher);
        this.category = category;
    }

    @Override
    public Set<Meal> getMeals() {
        return getFetcher().getMealsByCategory(category);
    }
}
