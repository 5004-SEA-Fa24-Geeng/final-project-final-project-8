package model.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IRecipeModel {

    /**
     * Get all ingredients from online API.
     * */
    Set<Ingredient> getAllIngredients() throws IOException;

    /**
     * Get all categories from online API.
     * */
    Set<String> getAllCategories() throws IOException;

    /**
     * Get all areas from online API.
     * */
    Set<String> getAllAreas() throws IOException;

    /** Get the meals based on user input */
    Set<Meal> processMeals(Set<Ingredient> userIngredients, String category, String area) throws IOException;


    /** Find the intersection of multiple meal sets.*/
    Set<Meal> findIntersection(List<Set<Meal>> mealSets);

    /**
     * Sets the list of new ingredients selected by the user.
     *
     * @param userIngredients A list of {@code Ingredient} objects representing the user's selected ingredients.
     */
    void setUserIngredients(Set<Ingredient> userIngredients);

    /**
     * Sets the category that the user selected.
     *
     * @param category The category that the user selected.
     */
    void setUserCategory(String category);

    /**
     * Sets the area that the user selected.
     *
     * @param area The category that the user selected.
     */
    void setUserArea(String area);



    /**
     * Get a set of meal objects that contain specific ingredients.
     *
     * @param userIngredients The {@code Ingredient} that user selected.
     * @return A set of meal objects corresponding to that contain the given ingredient.
     */
    Set<Meal> getMealsByIngredient(Set<Ingredient> userIngredients) throws IOException;

    /**
     * Get a set of meal objects that belongs to specific category.
     *
     * @param category the category that user selected.
     * @return A set of meal objects corresponding to that contain the given category.
     */
    Set<Meal> getMealsByCategory(String category) throws IOException;

    /**
     * Get a set of meal objects that belongs to specific area.
     *
     * @param area the area that user selected.
     * @return A set of meal objects corresponding to that contain the given area.
     */
    Set<Meal> getMealsByArea(String area) throws IOException;


    /**
     * Get meals which exist in both given sets.
     *
     * @param mealSet1 the first given set of meals
     * @param mealSet2 the second given set of meals
     * @return A set of meal objects that exist in both given sets
     */
    Set<Meal> getMutualMeals(Set<Meal> mealSet1, Set<Meal> mealSet2, Set<Meal> mealSet3);



    /**
     * Retrieves a recipe based on a given meal ID.
     *
     * @param idMeal The unique identifier of the meal that the user selected.
     * @return A {@code Recipe} object corresponding to the given meal ID.
     */
    Recipe getRecipeByIdMeal(int idMeal) throws IOException;


}



