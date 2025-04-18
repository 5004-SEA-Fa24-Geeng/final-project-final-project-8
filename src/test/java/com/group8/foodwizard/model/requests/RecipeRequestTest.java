package com.group8.foodwizard.model.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.group8.foodwizard.model.recipe.Ingredient;

/**
 * Test class for {@link RecipeRequest}.
 */
public class RecipeRequestTest {

    /**
     * The RecipeRequest instance used for testing.
     */
    private RecipeRequest recipeRequest;

    /**
     * Sets up the test environment before each test method.
     * Initializes a new RecipeRequest object.
     */
    @BeforeEach
    public void setUp() {
        recipeRequest = new RecipeRequest();
    }

    /**
     * Tests the default constructor of RecipeRequest.
     * Verifies that the ingredients set is initialized and empty,
     * and that region and category are null.
     */
    @Test
    public void testDefaultConstructor() {
        assertNotNull(recipeRequest.getIngredients());
        assertTrue(recipeRequest.getIngredients().isEmpty());
        assertEquals(null, recipeRequest.getRegion());
        assertEquals(null, recipeRequest.getCategory());
    }

    /**
     * Tests the getter and setter for the region property.
     * Verifies that the region can be set and retrieved correctly,
     * including handling null values.
     */
    @Test
    public void testRegionGetterAndSetter() {
        String testRegion = "Italian";
        recipeRequest.setRegion(testRegion);
        assertEquals(testRegion, recipeRequest.getRegion());

        // Test with null value
        recipeRequest.setRegion(null);
        assertEquals(null, recipeRequest.getRegion());
    }

    /**
     * Tests the getter and setter for the category property.
     * Verifies that the category can be set and retrieved correctly,
     * including handling null values.
     */
    @Test
    public void testCategoryGetterAndSetter() {
        String testCategory = "Dessert";
        recipeRequest.setCategory(testCategory);
        assertEquals(testCategory, recipeRequest.getCategory());

        // Test with null value
        recipeRequest.setCategory(null);
        assertEquals(null, recipeRequest.getCategory());
    }

    /**
     * Tests the getter and setter for the ingredients property.
     * Verifies that the ingredients set can be set and retrieved correctly,
     * including handling empty sets and null values.
     */
    @Test
    public void testIngredientsGetterAndSetter() {
        // Create test ingredients set
        Set<Ingredient> testIngredients = new HashSet<>();
        testIngredients.add(new Ingredient("1", "Chicken", "chicken.jpg"));
        testIngredients.add(new Ingredient("2", "Tomato", "tomato.jpg"));

        // Set and verify
        recipeRequest.setIngredients(testIngredients);
        assertEquals(testIngredients, recipeRequest.getIngredients());
        assertEquals(2, recipeRequest.getIngredients().size());

        // Test with empty set
        Set<Ingredient> emptyIngredients = new HashSet<>();
        recipeRequest.setIngredients(emptyIngredients);
        assertEquals(emptyIngredients, recipeRequest.getIngredients());
        assertEquals(0, recipeRequest.getIngredients().size());

        // Test with null value (should be avoided in practice, but testing for
        // robustness)
        recipeRequest.setIngredients(null);
        assertEquals(null, recipeRequest.getIngredients());
    }

    /**
     * Tests setting all properties of the RecipeRequest object.
     * Verifies that region, category, and ingredients can be set together
     * and retrieved correctly.
     */
    @Test
    public void testCompleteRequest() {
        // Create a complete request object
        String testRegion = "Mexican";
        String testCategory = "Main";
        Set<Ingredient> testIngredients = new HashSet<>();
        testIngredients.add(new Ingredient("3", "Beef", "beef.jpg"));
        testIngredients.add(new Ingredient("4", "Onion", "onion.jpg"));

        // Set all properties
        recipeRequest.setRegion(testRegion);
        recipeRequest.setCategory(testCategory);
        recipeRequest.setIngredients(testIngredients);

        // Verify all properties
        assertEquals(testRegion, recipeRequest.getRegion());
        assertEquals(testCategory, recipeRequest.getCategory());
        assertEquals(testIngredients, recipeRequest.getIngredients());
        assertEquals(2, recipeRequest.getIngredients().size());
    }
}
