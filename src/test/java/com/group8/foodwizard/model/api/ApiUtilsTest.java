package com.group8.foodwizard.model.api;

import com.group8.foodwizard.model.recipe.Meal;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ApiUtilsTest {

    @Test
    void testGetAllIngredients() {
        InputStream result = ApiUtils.getAllIngredients();
        assertNotNull(result);
    }

    @Test
    void testGetAllCategories() {
        InputStream result = ApiUtils.getAllCategories();
        assertNotNull(result);
    }

    @Test
    void testGetAllAreas() {
        InputStream result = ApiUtils.getAllAreas();
        assertNotNull(result);
    }

    @Test
    void testCastIngredientName() {
        String ingredientName = "Chicken Breast";
        String newIngredientName = ApiUtils.castIngredientName(ingredientName);
        String expected = "chicken_breast";
        assertEquals(expected, newIngredientName);
    }

    @Test
    void testCastIngredientName2() {
        String ingredientName = "Pepper";
        String newIngredientName = ApiUtils.castIngredientName(ingredientName);
        String expected = "pepper";
        assertEquals(expected, newIngredientName);
    }

    @Test
    void testGetIdMealByIngredient() throws Exception {
        Set<Integer> mealIds = ApiUtils.getIdMealByIngredient("Chicken");
        assertTrue(mealIds.contains(52940), "Meal ID 52940 should be in the result.");
        assertTrue(mealIds.contains(52846), "Meal ID 52846 should be in the result.");
    }

    @Test
    void testGetRecipeByIdMeal() {
        InputStream result = ApiUtils.getRecipeByIdMeal(52940);
        assertNotNull(result);
    }

    @Test
    void testGetMealsSetByIngredient() throws IOException {
        Set<Meal> expected = new HashSet<>();
        expected.add(new Meal("Honey Balsamic Chicken with Crispy Broccoli & Potatoes",
                "https://www.themealdb.com/images/media/meals/kvbotn1581012881.jpg", "52993"));
        String ingredientName = "Balsamic Vinegar";
        try {
            Set<Meal> result = ApiUtils.getMealsByIngredient(ingredientName);
            assertEquals(expected, result);
        } catch (IOException e) {
            throw new IOException("exception");
        }
    }

    @Test
    void testMealsByCategory() {
        String category = "Chicken";
        InputStream result = ApiUtils.mealsByCategory(category);
        assertNotNull(result);
    }

    @Test
    void testMealsByArea() {
        String area = "Canadian";
        InputStream result = ApiUtils.mealsByCategory(area);
        assertNotNull(result);
    }

    @Test
    void testBadUrlContents() throws IOException {
        String badUrl = "ht!tp://bad-url";
        InputStream result = ApiUtils.getUrlContents(badUrl);
        assertNotNull(result);  // Should return InputStream.nullInputStream()
        assertEquals(0, result.readAllBytes().length);
    }

    @Test
    void testStatusNot200() {
        // Arrange
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
        // Act
        String badUrl = "https://httpstat.us/404"; // or any invalid URL that returns 404
        ApiUtils.getUrlContents(badUrl);
        // Assert
        String output = errContent.toString();
        assertTrue(output.contains("Failed to connect to " + badUrl));
        // Cleanup
        System.setErr(originalErr);
    }
}
