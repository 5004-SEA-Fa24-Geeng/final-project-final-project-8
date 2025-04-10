package com.group8.foodwizard.model.recipe;

import com.group8.foodwizard.model.api.ApiUtils;
import com.group8.foodwizard.model.formatter.JsonParser;
import com.group8.foodwizard.model.recipe.strategy.GetMealByArea;
import com.group8.foodwizard.model.recipe.strategy.GetMealByCategory;
import com.group8.foodwizard.model.recipe.strategy.GetMealByIngredient;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeModel implements IRecipeModel {

    /** A set of all ingredients. */
    private final Set<Ingredient> allIngredients;

    /** A set of all areas. */
    private final Set<String> allAreas;

    /** A set of all categories. */
    private final Set<String> allCategories;

    /** A set of ingredients the user selected. */
    Set<Ingredient> userIngredients;

    /** The category that the user selected. */
    String userCategory;

    /** The area that the user selected. */
    String userArea;

    /** The only instance of RecipeModel. */
    private static RecipeModel instance;

    /** List of Observers detects new user submissions. */
    private final List<Observer> observers = new ArrayList<>();

    /** the instance of cached fetcher. */
    private final CachedMealFetcher cachedMealFetcher = new CachedMealFetcher();

    RecipeModel() throws IOException {
        // Before entering the page, the model has been initialized (with a null userIngredients)
        // Convert InputStream to Set
        this.allIngredients = JsonParser.allIngredientsList(ApiUtils.getAllIngredients());
        this.allAreas = JsonParser.allAreasList(ApiUtils.getAllAreas());
        this.allCategories = JsonParser.allCategoriesList(ApiUtils.getAllCategories());
    }

    // Singleton Pattern: avoid reloading static data like ingredients, areas or categories multiple times
    public static synchronized RecipeModel getInstance() throws IOException {
        // Only one instance of RecipeModel can exist throughout the app
        if (instance == null) {
            instance = new RecipeModel();
        }
        return instance;
    }

    // call this method in Controller
    // e.g. RecipeModel.getInstance().addObserver(this);
    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update();
        }
    }

    /**
     * Get all ingredients from online API.
     * 
     * @return All ingredients.
     */
    @Override
    public Set<Ingredient> getAllIngredients() {
        return this.allIngredients;
    }

    /**
     * Get all categories from online API.
     * 
     * @return All categories.
     */
    @Override
    public Set<String> getAllCategories() {
        return this.allCategories;
    }

    /**
     * Get all areas from online API.
     * 
     * @return All areas.
     */
    @Override
    public Set<String> getAllAreas() {
        return this.allAreas;
    }

    /** Get the meals based on user input */
    @Override
    public Set<Meal> processMeals(Set<Ingredient> userIngredients, String category, String area) throws IOException {
        // pass user inputs into the model
        setUserIngredients(userIngredients);
        setUserCategory(category);
        setUserArea(area);

        // collect all non-empty meal sets
        List<Set<Meal>> mealSets = new ArrayList<>();

        // if the user selected ingredients, apply IngredientFilterStrategy
        if (this.userIngredients != null) {
            mealSets.add(new GetMealByIngredient(this.userIngredients, cachedMealFetcher).getMeals());
        }

        // if the user selected a category, apply CategoryFilterStrategy
        if (this.userCategory != null) {
            mealSets.add(new GetMealByCategory(this.userCategory, cachedMealFetcher).getMeals());
        }

        // if the user selected an area, apply AreaFilterStrategy
        if (this.userArea != null) {
            mealSets.add(new GetMealByArea(this.userArea, cachedMealFetcher).getMeals());
        }

        // handle different cases of user inputs
        return switch (mealSets.size()) {
            case 0 -> Collections.emptySet();
            case 1 -> mealSets.get(0);
            default -> findIntersection(mealSets);
        };
    }

    /** Find the intersection of multiple meal sets. */
    @Override
    public Set<Meal> findIntersection(List<Set<Meal>> mealSets) {
        if (mealSets.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Meal> intersection = new HashSet<>(mealSets.get(0));
        for (int i = 1; i < mealSets.size(); i++) {
            intersection.retainAll(mealSets.get(i));
        }
        return intersection;
    }

    /**
     * Sets the list of new ingredients selected by the user.
     *
     * @param userIngredients A list of {@code Ingredient} objects representing the user's selected ingredients.
     */
    @Override
    public void setUserIngredients(Set<Ingredient> userIngredients) {
        this.userIngredients = userIngredients;
        notifyObservers();
    }

    /**
     * Sets the category selected by the user.
     *
     * @param category The category selected by the user.
     */
    @Override
    public void setUserCategory(String category) {
        this.userCategory = category;
        notifyObservers();
    }

    /**
     * Sets the area selected by the user.
     *
     * @param area The area selected by the user.
     */
    @Override
    public void setUserArea(String area) {
        this.userArea = area;
        notifyObservers();
    }

    /**
     * Get meals that contain specific ingredients.
     * 
     * @param userIngredients The ingredients selected by the user.
     * @return A set of meals matching those ingredients.
     *
     */
    @Override
    public Set<Meal> getMealsByIngredient(Set<Ingredient> userIngredients) {
        return cachedMealFetcher.getMealsByIngredient(userIngredients);
    }

    /**
     * Get meals that belong to a specific category.
     * 
     * @param category The selected category.
     * @return A set of meals in that category.
     *
     */
    @Override
    public Set<Meal> getMealsByCategory(String category) {
        return cachedMealFetcher.getMealsByCategory(category);
    }

    /**
     * Get meals that belong to a specific area.
     * 
     * @param area The selected area.
     * @return A set of meals in that area.
     *
     */
    @Override
    public Set<Meal> getMealsByArea(String area) {
        return cachedMealFetcher.getMealsByArea(area);
    }

    /**
     * Get meals which exist in both given sets.
     *
     * @param mealSet1 the first given set of meals
     * @param mealSet2 the second given set of meals
     * @return A set of meal objects that exist in both given sets
     */
    @Override
    public Set<Meal> getMutualMeals(Set<Meal> mealSet1, Set<Meal> mealSet2, Set<Meal> mealSet3) {
        return mealSet1.stream()
                .filter(mealSet2::contains)
                .filter(mealSet3::contains)
                .collect(Collectors.toSet());
    }

    /**
     * Retrieves a recipe based on a given meal ID.
     *
     * @param idMeal A set of meal ids.
     * @return A {@code Recipe} object corresponding to the given meal ID, or {@code null} if not found.
     */
    @Override
    public Recipe getRecipeByIdMeal(int idMeal) throws IOException {
        // get recipe data and map: Map<String, Object> extractRecipeData(InputStream input)
        Map<String, Object> recipeData = JsonParser.extractRecipeData(ApiUtils.getRecipeByIdMeal(idMeal));
        // get recipe object: Recipe mapToRecipe(Map<String, Object> recipeData)
        return JsonParser.mapToRecipe(recipeData);
    }

    /** Nested class for caching. */
    public static class CachedMealFetcher {
        private final Map<String, Set<Meal>> ingredientCache = new HashMap<>();
        private final Map<String, Set<Meal>> categoryCache = new HashMap<>();
        private final Map<String, Set<Meal>> areaCache = new HashMap<>();

        public Set<Meal> getMealsByIngredient(Set<Ingredient> userIngredients) {
            Set<Meal> mealSetOfUserIngredients = new HashSet<>();

            for (Ingredient ingredient : userIngredients) {
                String existingName = ingredient.nameIngredient();
                Set<Meal> mealSet = ingredientCache.computeIfAbsent(existingName, newName -> {
                    try {
                        return ApiUtils.getMealsByIngredient(newName);
                    } catch (IOException e) {
                        return Set.of();
                    }
                });
                mealSetOfUserIngredients.addAll(mealSet);
            }
            return mealSetOfUserIngredients;
        }

        public Set<Meal> getMealsByCategory(String category) {
            return categoryCache.computeIfAbsent(category, c -> {
                try {
                    return JsonParser.extractMeals(ApiUtils.mealsByCategory(c));
                } catch (IOException e) {
                    return Set.of();
                }
            });
        }

        public Set<Meal> getMealsByArea(String area) {
            return areaCache.computeIfAbsent(area, a -> {
                try {
                    return JsonParser.extractMeals(ApiUtils.mealsByArea(a));
                } catch (IOException e) {
                    return Set.of();
                }
            });
        }
    }

}
