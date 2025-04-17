package com.group8.foodwizard.model.recipe.strategy;

import com.group8.foodwizard.model.recipe.Meal;
import com.group8.foodwizard.model.recipe.RecipeModel.CachedMealFetcher;


import java.util.Set;

public class GetMealByArea extends GetMealStrategy {
    /** The area that the user selected. */
    private final String area;

    /**  Constructs a GetMealByArea with the given area and cached fetcher.
     *
     * @param area the area that the user selected
     * @param fetcher the fetcher to fetch the cached data
     * */
    public GetMealByArea(String area, CachedMealFetcher fetcher) {
        super(fetcher);
        this.area = area;
    }

    @Override
    public Set<Meal> getMeals() {
        return getFetcher().getMealsByArea(area);
    }
}
