package model.formatter;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
}