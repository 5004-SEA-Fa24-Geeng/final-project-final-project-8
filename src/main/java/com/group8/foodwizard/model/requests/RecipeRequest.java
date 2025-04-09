package com.group8.foodwizard.model.requests;

import com.group8.foodwizard.model.recipe.Ingredient;
import java.util.HashSet;
import java.util.Set;

public class RecipeRequest {
    private Set<Ingredient> ingredients;
    private String region;
    private String category;

    public RecipeRequest() {
        ingredients = new HashSet<>();
        region = null;
        category = null;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
