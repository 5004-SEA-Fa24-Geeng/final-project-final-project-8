package model.recipe;

import java.io.IOException;
import java.util.*;

public class MockRecipeModel implements IRecipeModel {

    private Set<Ingredient> userIngredients;
    private String userCategory;
    private String userArea;

    private final Ingredient mockIngredient = new Ingredient("202", "Lime", "www.themealdb.com/images/ingredients/lime-medium.png");

    private final Meal mockMeal = new Meal(
            "Chick-Fil-A Sandwich",
            "https://www.themealdb.com/images/media/meals/sbx7n71587673021.jpg",
            "53016"
    );

    private final Recipe mockRecipe = new Recipe(
            53016,
            "Chick-Fil-A Sandwich",
            "Chicken",
            "American",
            "Mock instructions",
            "https://youtube.com/mock",
            "https://www.themealdb.com/images/media/meals/sbx7n71587673021.jpg",
            List.of("Lime"),
            List.of("1 slice")
    );

    @Override
    public Set<Ingredient> getAllIngredients() {
        return Set.of(mockIngredient);
    }

    @Override
    public Set<String> getAllCategories() {
        return Set.of("Chicken", "Beef");
    }

    @Override
    public Set<String> getAllAreas() {
        return Set.of("American", "British");
    }

    @Override
    public Set<Meal> processMeals(Set<Ingredient> userIngredients, String category, String area) {
        setUserIngredients(userIngredients);
        setUserCategory(category);
        setUserArea(area);

        // Return mock meal if Lime is selected
        if (userIngredients != null && userIngredients.stream().anyMatch(i -> i.nameIngredient().equals("Lime"))) {
            return Set.of(mockMeal);
        }

        return Set.of();
    }

    @Override
    public Set<Meal> findIntersection(List<Set<Meal>> mealSets) {
        if (mealSets.isEmpty()) return Collections.emptySet();
        Set<Meal> result = new HashSet<>(mealSets.get(0));
        for (int i = 1; i < mealSets.size(); i++) {
            result.retainAll(mealSets.get(i));
        }
        return result;
    }

    @Override
    public void setUserIngredients(Set<Ingredient> userIngredients) {
        this.userIngredients = userIngredients;
    }

    @Override
    public void setUserCategory(String category) {
        this.userCategory = category;
    }

    @Override
    public void setUserArea(String area) {
        this.userArea = area;
    }

    @Override
    public Set<Meal> getMealsByIngredient(Set<Ingredient> userIngredients) {
        if (userIngredients.contains(mockIngredient)) {
            return Set.of(mockMeal);
        }
        return Set.of();
    }

    @Override
    public Set<Meal> getMealsByCategory(String category) {
        if ("Chicken".equals(category)) {
            return Set.of(mockMeal);
        }
        return Set.of();
    }

    @Override
    public Set<Meal> getMealsByArea(String area) {
        if ("American".equals(area)) {
            return Set.of(mockMeal);
        }
        return Set.of();
    }

    @Override
    public Set<Meal> getMutualMeals(Set<Meal> mealSet1, Set<Meal> mealSet2, Set<Meal> mealSet3) {
        Set<Meal> intersection = new HashSet<>(mealSet1);
        intersection.retainAll(mealSet2);
        intersection.retainAll(mealSet3);
        return intersection;
    }

    @Override
    public Recipe getRecipeByIdMeal(int idMeal) {
        if (idMeal == 53016) {
            return mockRecipe;
        }
        return null;
    }
}
