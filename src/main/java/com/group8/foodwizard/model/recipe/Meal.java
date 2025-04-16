package com.group8.foodwizard.model.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Represents a simplified view of a meal, typically used for displaying previews
 * such as in recipe search results.
 *
 * <p>This record contains:
 * <ul>
 *     <li>{@code mealName} - the name of the meal</li>
 *     <li>{@code mealImg} - a URL pointing to the meal's thumbnail image</li>
 *     <li>{@code idMeal} - the unique identifier of the meal</li>
 * </ul>
 *
 * <p>The class is annotated to ignore unknown JSON properties during deserialization
 * and enforce property order when serialized.
 *
 * @param mealName the name of the meal
 * @param mealImg the URL of the meal's thumbnail image
 * @param idMeal the unique ID of the meal
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "mealName", "mealImg", "idMeal" })
public record Meal(String mealName, String mealImg, String idMeal) {
}
