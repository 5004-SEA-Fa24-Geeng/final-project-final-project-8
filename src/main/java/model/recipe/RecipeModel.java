package model.recipe;

import model.api.ApiUtils;
import model.formatter.JsonParser;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

//        // from user input
//        this.userIngredients = userIngredients;
//        this.userCategory = userCategory;
//        this.userArea = userArea;


//        if (userArea != null) {
//
//        }

    }

    /**
     * Method to be called in Controller.
     * */
    @Override
    public void processRecipes() throws IOException {
        if (this.userIngredients != null) {
            setNewIngredients(this.userIngredients);
        }

        Set<Integer> allIdMeals =getIdMealByIngredient(this.userIngredients);
        setRecipesByIdMeal(allIdMeals);
    }


    /**
     * Sets the list of new ingredients selected by the user.
     *
     * @param userIngredients A list of {@code Ingredient} objects representing the user's selected ingredients.
     */
    @Override
    public void setNewIngredients(Set<Ingredient> userIngredients) {
        this.userIngredients = userIngredients;
    }


    /**
     * Retrieves a list of meal IDs that contain a specific ingredient.
     *
     * @param userIngredients The iIngredients that user selected.
     * @return A set of meal IDs corresponding to meals that contain the given ingredients.
     */
    @Override
    public Set<Integer> getIdMealByIngredient(Set<Ingredient> userIngredients) throws IOException {
        Set<Integer> allIdMeals = new HashSet<>();
        for (Ingredient ingr : userIngredients) {
            allIdMeals.addAll(ApiUtils.getIdMealByIngredient(ingr.nameIngredient()));
        }

        return allIdMeals;
    }

    @Override
    public void setRecipesByIdMeal(Set<Integer> idMeals) throws IOException {
        for (int id : idMeals) {
            // get recipe data and map: Map<String, Object> extractRecipeData(InputStream input)
            Map<String, Object> recipeData = JsonParser.extractRecipeData(ApiUtils.getRecipeByIdMeal(id));
            // get recipe object: Recipe mapToRecipe(Map<String, Object> recipeData)
            Recipe recipeObj = JsonParser.mapToRecipe(recipeData);
            this.recipes.add(recipeObj);
        }
    }

    /**
     * Retrieves a recipe based on a given meal ID.
     *
     * @param idMeal A set of meal ids.
     * @return A {@code Recipe} object corresponding to the given meal ID, or {@code null} if not found.
     */
    @Override
    public Recipe getRecipeByIdMeal(int idMeal) {
        return null; // to be discussed
    }

    /**
     * Groups recipes that contain a specific ingredient.
     *
     * @param ingredient The {@code Ingredient} used to filter recipes.
     * @return A set of {@code Recipe} objects that include the specified ingredient.
     */
    @Override
    public Set<Recipe> groupRecipeByIngredient(Ingredient ingredient) {
        return Set.of(); // to be discussed
    }

}
