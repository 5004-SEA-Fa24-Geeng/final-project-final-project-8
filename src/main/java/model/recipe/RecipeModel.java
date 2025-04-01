package model.recipe;

import model.api.ApiUtils;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class RecipeModel implements IRecipeModel {

    /** A set of all ingredients. */
    private Set<Ingredient> allIngredients;

    /** A set of ingredients the user selected. */
    private Set<Ingredient> userIngredients;

    /** A set of recipes based on user-selected ingredients. */
    private Set<Recipe> recipes;

    /** A set of all areas. */
    private Set<String> areas;

    /** A set of all categories. */
    private Set<String> categories;


    public RecipeModel(Set<Ingredient> userIngredients) {
        // Convert InputStream to Set
        this.allIngredients = JsonParser.allIngredientsList(ApiUtils.getAllIngredients());
        this.areas = JsonParser.allAreasList(ApiUtils.getAllAreas());
        this.categories = JsonParser.allCategoriesList(ApiUtils.getAllCategories());

        // Before entering the page, the model has been initialized (with a null userIngredients)
        if (userIngredients != null) {
            this.userIngredients = userIngredients;
        }
    }


    /**
     * Sets the set of new ingredients selected by the user.
     *
     * @param userIngredients A list of {@code Ingredient} objects representing the user's selected ingredients.
     */
    @Override
    public void setNewIngredients(Set<Ingredient> userIngredients) {
        // For ingredient in userIngredients:
        for (Ingredient ingr : userIngredients) {
            // Call set<Recipe> groupRecipeByIngredient

        }

    }

    /**
     * Groups recipes that contain a specific ingredient.
     *
     * @param ingredient The {@code Ingredient} used to filter recipes.
     * @return A set of {@code Recipe} objects that include the specified ingredient.
     */
    @Override
    public Set<Recipe> groupRecipeByIngredient(Ingredient ingredient) {
        return Set.of();
    }

    /**
     * Retrieves a list of meal IDs that contain a specific ingredient.
     *
     * @param ingredient The {@code Ingredient} used to search for meal IDs.
     * @return A list of meal ID strings corresponding to meals that contain the given ingredient.
     */
    @Override
    public List<String> getIdMealByIngredient(Set<String> userIngredients) throws IOException {
        // ApiUtils: Set<Integer> getIdMealByIngredient(String ingredientName)
        for (String ingr : userIngredients) {

            ApiUtils.getIdMealByIngredient(ingr);
        }
    }

    /**
     * Retrieves a recipe based on a given meal ID.
     *
     * @param idMeal The unique identifier of the meal.
     * @return A {@code Recipe} object corresponding to the given meal ID, or {@code null} if not found.
     */
    @Override
    public Recipe getRecipeByIdMeal(int idMeal) {
        return null;
    }
}
