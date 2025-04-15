package com.group8.foodwizard.model.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * Primary recipe to pass around between objects.
 * Is immutable and uses Jackson annotations for serialization.
 *
 * @param recipeId     the ID of the recipe
 * @param recipeName   the name of the recipe
 * @param instructions the instructions of the recipe
 * @param image        the image of the recipe
 * @param youtube      the video link of the recipe
 * @param category     the category of the recipe
 * @param area         the area of the recipe
 * @param ingredients  the ingredients of the recipe
 * @param measures     the portion of the ingredient
 * @return the recipe
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "recipeId", "recipeName", "instructions", "image",
        "youtube", "category", "area", "ingredients", "measures" })
public record Recipe(int recipeId, String recipeName, String instructions, String image,
        String youtube, String category, String area, List<String> ingredients, List<String> measures) {

}
