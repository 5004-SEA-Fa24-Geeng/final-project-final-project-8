package model.api;

import model.api.ApiUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ApiUtilsTest {

    @Test
    public void testGetAllIngredients() {
        String json = "{\"meals\": []}";
        InputStream mockInputStream = new ByteArrayInputStream(json.getBytes());

        InputStream result = ApiUtils.getAllIngredients();

        assertNotNull(result, "The InputStream should not be null.");
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
}
