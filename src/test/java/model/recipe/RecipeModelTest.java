package model.recipe;

import model.api.ApiUtils;
import model.formatter.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeModelTest {

    private RecipeModel recipeModel;
    private Set<Ingredient> testUserIngredients;

    @BeforeEach
    void setUp() throws IOException {
        testUserIngredients = new HashSet<>();
        testUserIngredients.add(new Ingredient("1", "Chicken", ""));
        testUserIngredients.add(new Ingredient("2", "Garlic", ""));

//        // Initialize RecipeModel with test ingredients
//        recipeModel = new RecipeModel(testUserIngredients);
    }

    @Test
    void testSetNewIngredients() {
        Set<Ingredient> newIngredients = new HashSet<>();
        newIngredients.add(new Ingredient("3", "Onion", ""));
        recipeModel.setNewIngredients(newIngredients);

        assertEquals(newIngredients, recipeModel.userIngredients);
    }

    @Test
    void testGetIdMealByIngredient() throws IOException {
        Set<Integer> mealIds = recipeModel.getIdMealByIngredient(testUserIngredients);
        assertNotNull(mealIds);
        assertFalse(mealIds.isEmpty()); // Ensure some meal IDs are retrieved
    }

    @Test
    void testProcessRecipes() throws IOException {
        recipeModel.processRecipes();
        assertNotNull(recipeModel.recipes);
        assertFalse(recipeModel.recipes.isEmpty()); // Should have recipes after processing
    }
}
