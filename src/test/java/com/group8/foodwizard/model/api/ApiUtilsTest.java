package com.group8.foodwizard.model.api;

import com.group8.foodwizard.model.api.ApiUtils;
import com.group8.foodwizard.model.recipe.Meal;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ApiUtilsTest {

    @Test
    public void testGetAllIngredients() {
        String json = "{\"meals\": []}";
        InputStream mockInputStream = new ByteArrayInputStream(json.getBytes());
        InputStream result = ApiUtils.getAllIngredients();
        assertNotNull(result);
        assertNotNull(mockInputStream);
    }

    @Test
    public void testGetAllCategories() {
        String json = "{\"meals\": []}";
        InputStream mockInputStream = new ByteArrayInputStream(json.getBytes());
        InputStream result = ApiUtils.getAllCategories();
        assertNotNull(result);
        assertNotNull(mockInputStream);
    }

    @Test
    public void testGetAllAreas() {
        String json = "{\"meals\": []}";
        InputStream mockInputStream = new ByteArrayInputStream(json.getBytes());
        InputStream result = ApiUtils.getAllAreas();
        assertNotNull(result);
        assertNotNull(mockInputStream);
    }

    @Test
    public void testCastIngredientName() {
        String ingredientName = "Chicken Breast";
        String newIngredientName = ApiUtils.castIngredientName(ingredientName);
        String expected = "chicken_breast";
        assertEquals(expected, newIngredientName);
    }

    @Test
    public void testCastIngredientName2() {
        String ingredientName = "Pepper";
        String newIngredientName = ApiUtils.castIngredientName(ingredientName);
        String expected = "pepper";
        assertEquals(expected, newIngredientName);
    }

    @Test
    public void testGetIdMealByIngredient() throws Exception {
        String json = "{ \"meals\": [ {\"idMeal\": \"52940\"}, {\"idMeal\": \"52846\"} ] }";
        InputStream mockInputStream = new ByteArrayInputStream(json.getBytes());

        Set<Integer> mealIds = ApiUtils.getIdMealByIngredient("Chicken");

        assertTrue(mealIds.contains(52940), "Meal ID 52940 should be in the result.");
        assertTrue(mealIds.contains(52846), "Meal ID 52846 should be in the result.");
    }

    @Test
    public void testGetRecipeByIdMeal() {
        // A simple test to confirm the method works without checking the content
        InputStream mockInputStream = new ByteArrayInputStream("{}".getBytes());

        // Direct test on the method
        InputStream result = ApiUtils.getRecipeByIdMeal(52940);

        assertNotNull(result, "The InputStream should not be null.");
    }

    @Test
    public void testGetMealsSetByIngredient() throws IOException {
        Set<Meal> expected = new HashSet<>();
        expected.add(new Meal("Honey Balsamic Chicken with Crispy Broccoli & Potatoes",
                "https://www.themealdb.com/images/media/meals/kvbotn1581012881.jpg","52993"));
        String ingredientName = "Balsamic Vinegar";
        try {
            Set<Meal> result = ApiUtils.getMealsByIngredient(ingredientName);
            assertEquals(expected, result);
        }
        catch (IOException e) {
            throw new IOException("exception");
        }
    }

    @Test
    public void testMealsByCategory() {
        String category = "Chicken";
        InputStream result = ApiUtils.mealsByCategory(category);
        assertNotNull(result);
    }

    @Test
    public void testMealsByArea() {
        String area = "Canadian";
        InputStream result = ApiUtils.mealsByCategory(area);
        assertNotNull(result);
    }

    @Test
    public void testBadUrlContents() throws IOException {
        String badUrl = "ht!tp://bad-url";
        InputStream result = ApiUtils.getUrlContents(badUrl);
        assertNotNull(result);  // Should return InputStream.nullInputStream()
        assertEquals(0, result.readAllBytes().length);
    }
}