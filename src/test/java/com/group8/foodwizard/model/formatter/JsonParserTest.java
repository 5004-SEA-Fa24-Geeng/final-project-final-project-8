package com.group8.foodwizard.model.formatter;

import com.group8.foodwizard.model.api.ApiUtils;
import com.group8.foodwizard.model.recipe.Ingredient;
import com.group8.foodwizard.model.recipe.Meal;
import com.group8.foodwizard.model.recipe.Recipe;
import com.group8.foodwizard.model.formatter.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    private static final String AREAS_JSON = """
                {
                    "meals": [
                        {"strArea": "American"},
                        {"strArea": "Italian"},
                        {"strArea": "Mexican"}
                    ]
                }
            """;

    private static final String CATEGORIES_JSON = """
                {
                    "meals": [
                        {"strCategory": "Dessert"},
                        {"strCategory": "Seafood"},
                        {"strCategory": "Vegetarian"}
                    ]
                }
            """;

    private static final String INGREDIENT_IMAGE = "www.themealdb.com/images/ingredients/%s-medium.png";

    @Test
    void extractIdMeal() throws IOException {
        // Test data in JSON format
        String json = "{\n" +
                "  \"meals\": [\n" +
                "    {\"idMeal\": \"52940\"},\n" +
                "    {\"idMeal\": \"52846\"}\n" +
                "  ]\n" +
                "}";

        // Convert the JSON string to an InputStream
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());

        // Act: Call the method you want to test
        Set<Integer> mealIds = JsonParser.extractIdMeal(inputStream);

        // Assert: Check if the set contains the expected idMeal values
        assertTrue(mealIds.contains(52940));
        assertTrue(mealIds.contains(52846));
    }

    @Test
    void testAllAreasList() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(AREAS_JSON.getBytes());
        Set<String> result = JsonParser.allAreasList(inputStream);
        assertNotNull(result);
        assertEquals(Set.of("American", "Italian", "Mexican"), result);
    }

    @Test
    void testAllCategoriesList() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(CATEGORIES_JSON.getBytes());
        Set<String> result = JsonParser.allCategoriesList(inputStream);
        assertNotNull(result);
        assertEquals(Set.of("Dessert", "Seafood", "Vegetarian"), result);
    }

    @Test
    void testExtractMeals_validJson() throws Exception {
        String json = "{\n" +
                "  \"meals\": [\n" +
                "    {\"strMeal\": \"Chicken Couscous\",\n" +
                "    \"strMealThumb\": \"https://www.themealdb.com/images/media/meals/qxytrx1511304021.jpg\",\n" +
                "    \"idMeal\": \"52846\"}\n" +
                "  ]\n" +
                "}";
        InputStream input = new ByteArrayInputStream(json.getBytes());

        Set<Meal> meals = JsonParser.extractMeals(input);

        assertEquals(1, meals.size());
    }

    @Test
    void testExtractMeals_emptyJson() throws Exception {
        String json = "{\"meals\":[]}";
        InputStream input = new ByteArrayInputStream(json.getBytes());

        Set<Meal> meals = JsonParser.extractMeals(input);

        assertTrue(meals.isEmpty());
    }

    @Test
    void testExtractMeals_missingMealsField() throws Exception {
        String json = "{}";
        InputStream input = new ByteArrayInputStream(json.getBytes());

        Set<Meal> meals = JsonParser.extractMeals(input);

        assertTrue(meals.isEmpty());
    }

    @Test
    void testExtractRecipeData_validJson() throws Exception {
        String json = "{\n" +
                "  \"meals\": [\n" +
                "    {\"idMeal\": \"52940\",\n" +
                "    \"strMeal\": \"Pasta Carbonara\",\n" +
                "    \"strCategory\": \"Italian\",\n" +
                "    \"strArea\": \"Italy\",\n" +
                "    \"strMealThumb\": \"https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg\",\n" +
                "    \"strYoutube\": \"https://www.youtube.com/watch?v=3AAdKl1UYZs\",\n" +
                "    \"strInstructions\": \"Boil pasta and mix with sauce\",\n" +
                "    \"strIngredient1\": \"Pasta\",\n" +
                "    \"strMeasure1\": \"200g\"}\n" +
                "  ]\n" +
                "}";
        InputStream input = new ByteArrayInputStream(json.getBytes());

        Map<String, Object> recipeData = JsonParser.extractRecipeData(input);

        assertEquals(52940, recipeData.get("recipeId"));
        assertEquals("Pasta Carbonara", recipeData.get("mealName"));
        assertEquals("Italian", recipeData.get("category"));
        assertEquals("Italy", recipeData.get("area"));
        assertEquals("https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg", recipeData.get("image"));
        assertEquals("https://www.youtube.com/watch?v=3AAdKl1UYZs", recipeData.get("youtube"));
        assertEquals("Boil pasta and mix with sauce", recipeData.get("instructions"));
        assertEquals(List.of("Pasta"), recipeData.get("ingredientsList"));
        assertEquals(List.of("200g"), recipeData.get("measuresList"));
    }

    @Test
    void testMapToRecipe() {
        Map<String, Object> recipeData = new HashMap<>();
        recipeData.put("recipeId", 52940);
        recipeData.put("mealName", "Pasta Carbonara");
        recipeData.put("category", "Pork");
        recipeData.put("area", "Italy");
        recipeData.put("instructions", "Boil pasta and mix with sauce");
        recipeData.put("youtube", "https://www.youtube.com/watch?v=3AAdKl1UYZs");
        recipeData.put("image", "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg");
        recipeData.put("ingredientsList", List.of("Pasta"));
        recipeData.put("measuresList", List.of("200g"));

        Recipe recipe = JsonParser.mapToRecipe(recipeData);
        assertEquals(52940, recipe.recipeId());
        assertEquals("Pasta Carbonara", recipe.recipeName());
        assertEquals("Pork", recipe.category());
        assertEquals("Italy", recipe.area());
        assertEquals("Boil pasta and mix with sauce", recipe.instructions());
    }

    @Test
    public void testAllIngredientsList() throws Exception {
        // Arrange: create mock JSON input
        String mockJson = """
            {
                "meals": [
                    {
                        "idIngredient": "1",
                        "strIngredient": "Chicken"
                    },
                    {
                        "idIngredient": "2",
                        "strIngredient": "Beef"
                    }
                ]
            }
        """;

        InputStream input = new ByteArrayInputStream(mockJson.getBytes());

        // Expected image URLs (based on how INGREDIENT_IMAGE and ApiUtils.castIngredientName work)
        String imageUrl1 = String.format(INGREDIENT_IMAGE, ApiUtils.castIngredientName("Chicken"));
        String imageUrl2 = String.format(INGREDIENT_IMAGE, ApiUtils.castIngredientName("Beef"));

        // Act
        Set<Ingredient> result = JsonParser.allIngredientsList(input);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(new Ingredient("1", "Chicken", imageUrl1)));
        assertTrue(result.contains(new Ingredient("2", "Beef", imageUrl2)));
    }
}
