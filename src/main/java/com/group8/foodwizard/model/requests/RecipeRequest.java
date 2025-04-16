package com.group8.foodwizard.model.requests;

import com.group8.foodwizard.model.recipe.Ingredient;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a request object used to filter recipes based on selected ingredients,
 * region (area), and category.
 *
 * <p>This class is typically used as the payload for POST requests when retrieving
 * recipe previews that match user-selected filters.
 */
public class RecipeRequest {
    private Set<Ingredient> ingredients;
    private String region;
    private String category;

    /**
     * Constructs a new {@code RecipeRequest} with default values:
     * an empty ingredient set and null values for region and category.
     */
    public RecipeRequest() {
        ingredients = new HashSet<>();
        region = null;
        category = null;
    }

    /**
     * Returns the selected region (area) for the recipe filter.
     *
     * @return the region string, or {@code null} if not set
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region (area) to filter recipes by.
     *
     * @param region the region string to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Returns the selected category for the recipe filter.
     *
     * @return the category string, or {@code null} if not set
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category to filter recipes by.
     *
     * @param category the category string to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns the set of selected ingredients.
     *
     * @return a set of {@link Ingredient} objects
     */
    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Sets the ingredients to filter recipes by.
     *
     * @param ingredients a set of {@link Ingredient} objects
     */
    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
