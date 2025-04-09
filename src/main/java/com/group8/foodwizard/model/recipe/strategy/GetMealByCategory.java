package com.group8.foodwizard.model.recipe.strategy;

import com.group8.foodwizard.model.recipe.Meal;
import com.group8.foodwizard.model.recipe.RecipeModel.CachedMealFetcher;

import java.io.IOException;
import java.util.Set;

public class GetMealByCategory extends GetMealStrategy {

    private final String category;

    public GetMealByCategory(String category, CachedMealFetcher fetcher) {
        super(fetcher);
        this.category = category;
    }

    @Override
    public Set<Meal> getMeals() {
        return fetcher.getMealsByCategory(category);
    }
}
