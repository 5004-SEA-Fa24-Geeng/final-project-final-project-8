package model.recipe;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"mealName", "mealImg", "idMeal"})
public record Meal(String mealName, String mealImg, String idMeal) {
}
