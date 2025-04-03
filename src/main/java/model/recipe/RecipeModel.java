package model.recipe;

import model.api.ApiUtils;
import model.formatter.JsonParser;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeModel implements IRecipeModel {

    /** A set of all ingredients. */
    private Set<Ingredient> allIngredients;

    /** A set of all areas. */
    private Set<String> allAreas;

    /** A set of all categories. */
    private Set<String> allCategories;

    /** A set of ingredients the user selected. */
    Set<Ingredient> userIngredients;

    /** The category that the user selected. */
    String userCategory;

    /** The area that the user selected. */
    String userArea;


    public RecipeModel() throws IOException {
        // Before entering the page, the model has been initialized (with a null userIngredients)
        // Convert InputStream to Set
        this.allIngredients = JsonParser.allIngredientsList(ApiUtils.getAllIngredients());
        this.allAreas = JsonParser.allAreasList(ApiUtils.getAllAreas());
        this.allCategories = JsonParser.allCategoriesList(ApiUtils.getAllCategories());

    }

    /**
     * Get all ingredients from online API.
     * @return All ingredients.
     */
    @Override
    public Set<Ingredient> getAllIngredients() {
        return this.allIngredients;
    }

    /**
     * Get all categories from online API.
     * @return All categories.
     */
    @Override
    public Set<String> getAllCategories() {
        return this.allCategories;
    }

    /**
     * Get all areas from online API.
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

        // get meals if user inputs that field
        if (this.userIngredients != null) {
            mealSets.add(getMealsByIngredient(userIngredients));
        }
        if (this.userCategory != null) {
            mealSets.add(getMealsByCategory(userCategory));
        }
        if (this.userArea != null) {
            mealSets.add(getMealsByCategory(userArea));
        }

        // handle different cases of user inputs
        switch (mealSets.size()) {
            case 0:
                return Collections.emptySet();
            case 1:
                return mealSets.get(0);
            default:
                return findIntersection(mealSets);
        }
    }

    /** Find the intersection of multiple meal sets.*/
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
    }


    /**
     * Sets the category selected by the user.
     *
     * @param category The category selected by the user.
     */
    @Override
    public void setUserCategory(String category) {
        this.userCategory = category;
    }

    /**
     * Sets the area selected by the user.
     *
     * @param area The area selected by the user.
     */
    @Override
    public void setUserArea(String area) {
        this.userArea = area;
    }

    /**
     * Get a set of meal objects that contain specific ingredients.
     *
     * @param userIngredients The {@code Ingredient} that user selected.
     * @return A set of meal objects corresponding to that contain the given ingredient.
     */
    @Override
    public Set<Meal> getMealsByIngredient(Set<Ingredient> userIngredients) throws IOException {
        return Set.of();  // not yet implemented
    }

    /**
     * Get a set of meal objects that belongs to specific category.
     *
     * @param category the category that user selected.
     * @return A set of meal objects corresponding to that contain the given category.
     */
    @Override
    public Set<Meal> getMealsByCategory(String category) throws IOException {
        return Set.of();  // not yet implemented
    }

    /**
     * Get a set of meal objects that belongs to specific area.
     *
     * @param area the area that user selected.
     * @return A set of meal objects corresponding to that contain the given area.
     */
    @Override
    public Set<Meal> getMealsByArea(String area) throws IOException {
        return Set.of();  // not yet implemented
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
        Set<Meal> mutualMeals = mealSet1.stream()
                .filter(mealSet2::contains)
                .filter(mealSet3::contains)
                .collect(Collectors.toSet());
        return mutualMeals;
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
        Recipe recipeObj = JsonParser.mapToRecipe(recipeData);
        return recipeObj;
    }

}
