package model.recipe.strategy;

import model.recipe.Ingredient;
import model.recipe.Meal;
import model.recipe.RecipeModel.CachedMealFetcher;

import java.io.IOException;
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