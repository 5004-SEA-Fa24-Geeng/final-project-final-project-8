package com.group8.foodwizard.model.recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Primary ingredient to pass around between objects.
 * Is immutable and uses Jackson annotations for serialization.
 *
 * @param idIngredient   the ID of the ingredient
 * @param nameIngredient the name of the recipe
 * @param strImage       the instructions of the recipe
 * @return the ingredient
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "idIngredient", "nameIngredient", "strImage" })
public record Ingredient(String idIngredient, String nameIngredient, String strImage) {

}
