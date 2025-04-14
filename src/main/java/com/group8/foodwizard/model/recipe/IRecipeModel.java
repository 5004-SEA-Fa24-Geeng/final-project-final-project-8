package com.group8.foodwizard.model.recipe;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IRecipeModel {

    /**
     * Get all ingredients from online API.
     * 
     * @return All ingredients.
     */
    Set<Ingredient> getAllIngredients();

    /**
     * Get all categories from online API.
     * 
     * @return All categories.
     */
    Set<String> getAllCategories();

    /**
     * Get all areas from online API.
     * 
     * @return All areas.
     */
    Set<String> getAllAreas();

    /**
     * Get the meals based on user input.
     * 
     * @param userIngredients The ingredients the user selected.
     * @param category        The category the user selected.
     * @param area            The area the user selected.
     * @return A set of meals that match the inputs.
     * @throws IOException If API call fails.
     */
    Set<Meal> processMeals(Set<Ingredient> userIngredients, String category, String area) throws IOException;

    /**
     * Find the intersection of multiple meal sets.
     * 
     * @param mealSets A list of meal sets to intersect.
     * @return A set of meals that appear in all given sets.
     */
    Set<Meal> findIntersection(List<Set<Meal>> mealSets);

    /**
     * Sets the list of new ingredients selected by the user.
     * 
     * @param userIngredients A set of ingredients selected by the user.
     */
    void setUserIngredients(Set<Ingredient> userIngredients);

    /**
     * Sets the category selected by the user.
     * 
     * @param category The category selected by the user.
     */
    void setUserCategory(String category);

    /**
     * Sets the area selected by the user.
     * 
     * @param area The area selected by the user.
     */
    void setUserArea(String area);

    /**
     * Get meals that contain specific ingredients.
     * 
     * @param userIngredients The ingredients selected by the user.
     * @return A set of meals matching those ingredients.
     * @throws IOException If API call fails.
     */
    Set<Meal> getMealsByIngredient(Set<Ingredient> userIngredients) throws IOException;

    /**
     * Get meals that belong to a specific category.
     * 
     * @param category The selected category.
     * @return A set of meals in that category.
     * @throws IOException If API call fails.
     */
    Set<Meal> getMealsByCategory(String category) throws IOException;

    /**
     * Get meals that belong to a specific area.
     * 
     * @param area The selected area.
     * @return A set of meals in that area.
     * @throws IOException If API call fails.
     */
    Set<Meal> getMealsByArea(String area) throws IOException;

    /**
     * Retrieves a recipe based on a given meal ID.
     * 
     * @param idMeal The meal ID.
     * @return The corresponding Recipe object.
     * @throws IOException If API call fails.
     */
    Recipe getRecipeByIdMeal(int idMeal) throws IOException;

}
