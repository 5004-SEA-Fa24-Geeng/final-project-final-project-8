package com.group8.foodwizard.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.group8.foodwizard.controllers.RecipeController;
import com.group8.foodwizard.model.recipe.Ingredient;
import com.group8.foodwizard.model.recipe.Meal;
import com.group8.foodwizard.model.recipe.Recipe;
import com.group8.foodwizard.model.recipe.RecipeModel;
import com.group8.foodwizard.model.requests.RecipeRequest;

/**
 * Test class for RecipeController.
 */
@SpringBootTest
public class RecipeControllerTest {

    /** The RecipeController instance to test. */
    private RecipeController recipeController;
    /** The mocked RecipeModel instance. */
    private RecipeModel mockModel;

    /**
     * Sets up the test environment before each test.
     * Initializes the RecipeController and mocks the RecipeModel.
     */
    @BeforeEach
    public void setUp() {
        recipeController = new RecipeController();
        mockModel = mock(RecipeModel.class);
    }

    /**
     * Tests the getAllIngredients method for successful retrieval.
     */
    @Test
    public void testGetAllIngredients() {
        // Create test data
        Set<Ingredient> testIngredients = new HashSet<>();
        testIngredients.add(new Ingredient("1", "Chicken", "chicken.jpg"));
        testIngredients.add(new Ingredient("2", "Tomato", "tomato.jpg"));

        try (MockedStatic<RecipeModel> mockedStatic = Mockito.mockStatic(RecipeModel.class)) {
            // Mock the static getInstance method
            mockedStatic.when(RecipeModel::getInstance).thenReturn(mockModel);

            // Configure the mock behavior
            when(mockModel.getAllIngredients()).thenReturn(testIngredients);

            // Call the controller method
            Set<Ingredient> result = recipeController.getAllIngredients();

            // Verify the result
            assertEquals(2, result.size());
            assertEquals(testIngredients, result);
        }
    }

    /**
     * Tests the getAllIngredients method when an IOException occurs during model
     * initialization.
     * Expects an empty set as a result.
     */
    @Test
    public void testGetAllIngredientsWithIOException() {
        try (MockedStatic<RecipeModel> mockedStatic = Mockito.mockStatic(RecipeModel.class)) {
            // Mock the static getInstance method to throw IOException
            mockedStatic.when(RecipeModel::getInstance).thenThrow(new IOException("Failed to initialize model"));

            // Call the controller method that should handle the exception
            Set<Ingredient> result = recipeController.getAllIngredients();

            // Verify the result is an empty set as expected per the error handling
            assertEquals(0, result.size());
            assertEquals(Collections.emptySet(), result);
        }
    }

    /**
     * Tests the getAllCategories method for successful retrieval.
     */
    @Test
    public void testGetAllCategories() {
        // Create test data
        Set<String> testCategories = new HashSet<>();
        testCategories.add("Italian");
        testCategories.add("Chinese");

        try (MockedStatic<RecipeModel> mockedStatic = Mockito.mockStatic(RecipeModel.class)) {
            // Mock the static getInstance method
            mockedStatic.when(RecipeModel::getInstance).thenReturn(mockModel);

            // Configure the mock behavior
            when(mockModel.getAllCategories()).thenReturn(testCategories);

            // Call the controller method
            Set<String> result = recipeController.getAllCategories();

            // Verify the result
            assertEquals(2, result.size());
            assertEquals(testCategories, result);
        }
    }

    /**
     * Tests the getAllCategories method when an IOException occurs during model
     * initialization.
     * Expects an empty set as a result.
     */
    @Test
    public void testGetAllCategoriesWithIOException() {
        try (MockedStatic<RecipeModel> mockedStatic = Mockito.mockStatic(RecipeModel.class)) {
            // Mock the static getInstance method to throw IOException
            mockedStatic.when(RecipeModel::getInstance).thenThrow(new IOException("Failed to initialize model"));

            // Call the controller method that should handle the exception
            Set<String> result = recipeController.getAllCategories();

            // Verify the result is an empty set as expected per the error handling
            assertEquals(0, result.size());
            assertEquals(Collections.emptySet(), result);
        }
    }

    /**
     * Tests the getAllRegions method for successful retrieval.
     */
    @Test
    public void testGetAllRegions() {
        // Create test data
        Set<String> testRegions = new HashSet<>();
        testRegions.add("Italian");
        testRegions.add("Mexican");

        try (MockedStatic<RecipeModel> mockedStatic = Mockito.mockStatic(RecipeModel.class)) {
            // Mock the static getInstance method
            mockedStatic.when(RecipeModel::getInstance).thenReturn(mockModel);

            // Configure the mock behavior
            when(mockModel.getAllAreas()).thenReturn(testRegions);

            // Call the controller method
            Set<String> result = recipeController.getAllRegions();

            // Verify the result
            assertEquals(2, result.size());
            assertEquals(testRegions, result);
        }
    }

    /**
     * Tests the getAllRegions method when an IOException occurs during model
     * initialization.
     * Expects an empty set as a result.
     */
    @Test
    public void testGetAllRegionsWithIOException() {
        try (MockedStatic<RecipeModel> mockedStatic = Mockito.mockStatic(RecipeModel.class)) {
            // Mock the static getInstance method to throw IOException
            mockedStatic.when(RecipeModel::getInstance).thenThrow(new IOException("Failed to initialize model"));

            // Call the controller method that should handle the exception
            Set<String> result = recipeController.getAllRegions();

            // Verify the result is an empty set as expected per the error handling
            assertEquals(0, result.size());
            assertEquals(Collections.emptySet(), result);
        }
    }

    /**
     * Tests the getRecipePreviews method for successful retrieval based on request
     * criteria.
     */
    @Test
    public void testGetRecipePreviews() {
        // Create test data
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient("1", "Chicken", "chicken.jpg"));
        ingredients.add(new Ingredient("2", "Tomato", "tomato.jpg"));
        String category = "Dessert";
        String region = "Italian";

        Set<Meal> testMeals = new HashSet<>();
        testMeals.add(new Meal("Test Recipe", "test_image.jpg", "1"));

        RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setIngredients(ingredients);
        recipeRequest.setCategory(category);
        recipeRequest.setRegion(region);

        try (MockedStatic<RecipeModel> mockedStatic = Mockito.mockStatic(RecipeModel.class)) {
            // Mock the static getInstance method
            mockedStatic.when(RecipeModel::getInstance).thenReturn(mockModel);

            // Configure the mock behavior
            try {
                when(mockModel.processMeals(ingredients, category, region)).thenReturn(testMeals);
            } catch (IOException e) {
                // This exception won't occur during mocking, but we need to handle it
                // to satisfy the compiler since the actual method throws IOException
            }

            // Call the controller method
            Set<Meal> result = recipeController.getRecipePreviews(recipeRequest);

            // Verify the result
            assertEquals(1, result.size());
            assertEquals(testMeals, result);
        }
    }

    /**
     * Tests the getRecipePreviews method when an IOException occurs during model
     * initialization.
     * Expects an empty set as a result.
     */
    @Test
    public void testGetRecipePreviewsWithIOException() {
        // Create test request
        RecipeRequest recipeRequest = new RecipeRequest();
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient("1", "Chicken", "chicken.jpg"));
        recipeRequest.setIngredients(ingredients);
        recipeRequest.setCategory("Italian");
        recipeRequest.setRegion("Italian");

        try (MockedStatic<RecipeModel> mockedStatic = Mockito.mockStatic(RecipeModel.class)) {
            // Mock the static getInstance method to throw IOException
            mockedStatic.when(RecipeModel::getInstance).thenThrow(new IOException("Failed to initialize model"));

            // Call the controller method that should handle the exception
            Set<Meal> result = recipeController.getRecipePreviews(recipeRequest);

            // Verify the result is an empty set as expected per the error handling
            assertEquals(0, result.size());
            assertEquals(Collections.emptySet(), result);
        }
    }

    /**
     * Tests the getRecipeByIdMeal method for successful retrieval of a specific
     * recipe.
     */
    @Test
    public void testGetRecipeByIdMeal() {
        int mealId = 52823;
        Recipe testRecipe = new Recipe(
                mealId,
                "Salmon Prawn Risotto",
                "Melt the butter in a thick-based pan and gently cook the onion without colour until it is soft.",
                "https://www.themealdb.com/images/media/meals/xxrxux1503070723.jpg",
                "https://www.youtube.com/watch?v=V2PMvBv52IE",
                "Seafood",
                "Italian",
                List.of("butter", "onion"),
                List.of("50g/2oz", "1 finely chopped"));
        try (MockedStatic<RecipeModel> mockedStatic = Mockito.mockStatic(RecipeModel.class)) {
            // Mock the static getInstance method
            mockedStatic.when(RecipeModel::getInstance).thenReturn(mockModel);

            try {
                when(mockModel.getRecipeByIdMeal(mealId)).thenReturn(testRecipe);
            } catch (IOException e) {
                // This exception won't occur during mocking, but we need to handle it
                // to satisfy the compiler since the actual method throws IOException
            }

            Recipe result = recipeController.getRecipeByIdMeal(mealId);

            // Verify the result is null as expected per the error handling
            assertEquals(testRecipe, result);
        }
    }

    /**
     * Tests the getRecipeByIdMeal method when an IOException occurs during model
     * initialization.
     * Expects a null result.
     */
    @Test
    public void testGetRecipeByIdMealWithIOException() {
        // Test data
        int mealId = 1;

        try (MockedStatic<RecipeModel> mockedStatic = Mockito.mockStatic(RecipeModel.class)) {
            // Mock the static getInstance method to throw IOException
            mockedStatic.when(RecipeModel::getInstance).thenThrow(new IOException("Failed to initialize model"));

            // Call the controller method that should handle the exception
            Recipe result = recipeController.getRecipeByIdMeal(mealId);

            // Verify the result is null as expected per the error handling
            assertEquals(null, result);
        }
    }
}
