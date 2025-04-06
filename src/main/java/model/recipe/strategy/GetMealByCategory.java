package model.recipe.strategy;

import model.recipe.Meal;
import model.recipe.RecipeModel.CachedMealFetcher;

import java.io.IOException;
import java.util.Set;

public class GetMealByCategory extends GetMealStrategy {

    private final String category;

    public GetMealByCategory(String category, CachedMealFetcher fetcher) {
        super(fetcher);
        this.category = category;
    }

    @Override
    public Set<Meal> getMeals() throws IOException {
        return fetcher.getMealsByCategory(category);
    }
}
