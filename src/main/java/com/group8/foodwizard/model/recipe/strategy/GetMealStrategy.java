package com.group8.foodwizard.model.recipe.strategy;

import com.group8.foodwizard.model.recipe.Meal;
import com.group8.foodwizard.model.recipe.RecipeModel;

import java.util.Set;

public abstract class GetMealStrategy implements IGetMealStrategy {

    protected final RecipeModel.CachedMealFetcher fetcher;

    public GetMealStrategy(RecipeModel.CachedMealFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public abstract Set<Meal> getMeals();
}
