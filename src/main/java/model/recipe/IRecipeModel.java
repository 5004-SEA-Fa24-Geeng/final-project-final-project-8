package model.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Set;

public interface IRecipeModel {

    /**
     * Sets the list of new ingredients selected by the user.
     *
     * @param userIngredients A list of {@code Ingredient} objects representing the user's selected ingredients.
     */
    void setNewIngredients(List<Ingredient> userIngredients);


    /**
     * Groups recipes that contain a specific ingredient.
     *
     * @param ingredient The {@code Ingredient} used to filter recipes.
     * @return A set of {@code Recipe} objects that include the specified ingredient.
     */
    Set<Recipe> groupRecipeByIngredient(Ingredient ingredient);

    /**
     * Retrieves a list of meal IDs that contain a specific ingredient.
     *
     * @param ingredient The {@code Ingredient} used to search for meal IDs.
     * @return A list of meal ID strings corresponding to meals that contain the given ingredient.
     */
    List<String> getIdMealByIngredient(Ingredient ingredient);

    /**
     * Retrieves a recipe based on a given meal ID.
     *
     * @param idMeal The unique identifier of the meal.
     * @return A {@code Recipe} object corresponding to the given meal ID, or {@code null} if not found.
     */
    Recipe getRecipeByIdMeal(int idMeal);


    /**
     * Gets an instance of the IRecipeModel.
     * <p>
     * This method provides access to a singleton instance of the IRecipeModel. The implementation
     * of this method should ensure that only one instance of the model is created and used throughout
     * the application.
     * </p>
     * <p>
     * Typically, this method should load the model or initialize it if it hasn't been created already.
     * The implementation details will depend on the specific model being used.
     * </p>
     *
     * @return the instance of the IRecipeModel
     * @throws UnsupportedOperationException if the method is not implemented yet
     */
    static IRecipeModel getInstance() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}



