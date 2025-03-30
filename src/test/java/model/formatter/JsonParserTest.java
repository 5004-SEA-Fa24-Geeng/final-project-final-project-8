package model.formatter;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Set;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

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


}