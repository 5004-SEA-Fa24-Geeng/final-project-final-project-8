package com.group8.foodwizard.model.recipe.strategy;

import com.group8.foodwizard.model.recipe.Meal;
import com.group8.foodwizard.model.recipe.RecipeModel.CachedMealFetcher;

import java.io.IOException;
import java.util.Set;

public class GetMealByArea extends GetMealStrategy {

    private final String area;

    public GetMealByArea(String area, CachedMealFetcher fetcher) {
        super(fetcher);
        this.area = area;
    }

    @Override
    public Set<Meal> getMeals() {
        return fetcher.getMealsByArea(area);
    }
}
