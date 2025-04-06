package model.recipe.strategy;

import model.recipe.Meal;
import model.recipe.RecipeModel.CachedMealFetcher;

import java.io.IOException;
import java.util.Set;

public class GetMealByArea extends GetMealStrategy {

    private final String area;

    public GetMealByArea(String area, CachedMealFetcher fetcher) {
        super(fetcher);
        this.area = area;
    }

    @Override
    public Set<Meal> getMeals() throws IOException {
        return fetcher.getMealsByArea(area);
    }
}
