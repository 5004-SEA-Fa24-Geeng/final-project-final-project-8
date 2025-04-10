package com.group8.foodwizard.model.recipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RecipeModelTest {

    private RecipeModel mockModel;
    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private Meal meal1;
    private Meal meal2;
    private Meal meal3;

    @BeforeEach
    void setUp() throws IOException {
        mockModel = new RecipeModel();

        ingredient1 = new Ingredient("1", "Chicken", "");
        ingredient2 = new Ingredient("2", "Rice", "");

        meal1 = new Meal("Chicken Stew", "thumb1", "101");
        meal2 = new Meal("Chicken Rice", "thumb2", "102");
        meal3 = new Meal("Pasta", "thumb3", "103");
    }

    @Test
    void testGetAllIngredients() {
        int exepctedAllIngredientsNumber = 575;
        assertEquals(exepctedAllIngredientsNumber, mockModel.getAllIngredients().size());
    }

    @Test
    void testGetAllCategories() {
        System.out.println(mockModel.getAllCategories());
        assertTrue(mockModel.getAllCategories().contains("Seafood"));
    }

    @Test
    void testGetAllAreas() {
        System.out.println(mockModel.getAllAreas());
        assertTrue(mockModel.getAllAreas().contains("Italian"));
    }

    @Test
    void testProcessMealsWithAllInputs() throws IOException {
        Set<Ingredient> userIngredients = Set.of(ingredient1);
        Set<Meal> result = mockModel.processMeals(userIngredients, "Main", "Italian");

        assertEquals(1, result.size());
        assertTrue(result.contains(meal1));
    }

    @Test
    void testProcessMealsWithOnlyIngredient() throws IOException {
        Set<Ingredient> userIngredients = Set.of(ingredient2);
        Set<Meal> result = mockModel.processMeals(userIngredients, null, null);

        assertEquals(1, result.size());
        assertTrue(result.contains(meal2));
    }

    @Test
    void testFindIntersection() {
        List<Set<Meal>> sets = List.of(
                Set.of(meal1, meal2),
                Set.of(meal1),
                Set.of(meal1, meal3)
        );

        Set<Meal> result = mockModel.findIntersection(sets);
        assertEquals(1, result.size());
        assertTrue(result.contains(meal1));
    }

    @Test
    void testGetMutualMeals() {
        Set<Meal> mutual = mockModel.getMutualMeals(
                Set.of(meal1, meal2),
                Set.of(meal1, meal3),
                Set.of(meal1)
        );

        assertEquals(1, mutual.size());
        assertTrue(mutual.contains(meal1));
    }

    @Test
    void testSettersNotifyObservers() {
        class TestObserver implements Observer {
            int updates = 0;
            public void update() { updates++; }
        }

        TestObserver observer = new TestObserver();
        mockModel.addObserver(observer);

        mockModel.setUserIngredients(Set.of(ingredient1));
        mockModel.setUserCategory("Main");
        mockModel.setUserArea("Italian");

        assertEquals(3, observer.updates); // Each setter triggers one update
    }

    @Test
    void testGetRecipeByIdMeal() throws IOException {
        Recipe recipe = mockModel.getRecipeByIdMeal(999);
        assertNotNull(recipe);
        assertEquals("Mock Recipe", recipe.recipeName());
    }


    // 1. Single ingredient, no category, no area
    @Test
    void testSingleIngredientOnly() throws IOException {
        Set<Meal> result = mockModel.processMeals(Set.of(ingredient1), null, null);
        assertEquals(Set.of(meal1), result);
    }

    // 2. Two ingredients, no category, no area
    @Test
    void testTwoIngredientsOnly() throws IOException {
        Set<Meal> result = mockModel.processMeals(Set.of(ingredient1, ingredient2), null, null);
        assertEquals(Set.of(meal1, meal2), result);
    }

    // 3. Single ingredient + one category, no area
    @Test
    void testSingleIngredientAndCategory() throws IOException {
        Set<Meal> result = mockModel.processMeals(Set.of(ingredient1), "Main", null);
        Set<Meal> expected = mockModel.findIntersection(
                List.of(Set.of(meal1), Set.of(meal1, meal3))
        );
        assertEquals(expected, result);
    }

    // 4. Two ingredients + one area, no category
    @Test
    void testTwoIngredientsAndArea() throws IOException {
        Set<Meal> result = mockModel.processMeals(Set.of(ingredient1, ingredient2), null, "Italian");
        Set<Meal> expected = mockModel.findIntersection(
                List.of(Set.of(meal1, meal2), Set.of(meal1, meal3))
        );
        assertEquals(expected, result);
    }

    // 5. Single ingredient + one category + one area
    @Test
    void testSingleIngredientCategoryArea() throws IOException {
        Set<Meal> result = mockModel.processMeals(Set.of(ingredient1), "Main", "Italian");
        Set<Meal> expected = mockModel.findIntersection(
                List.of(Set.of(meal1), Set.of(meal1, meal3), Set.of(meal1, meal3))
        );
        assertEquals(expected, result);
    }

    // 6. Two ingredients, no category, no area (same as test 2, included for clarity)
    @Test
    void testTwoIngredientsNoCategoryNoAreaRepeated() throws IOException {
        Set<Meal> result = mockModel.processMeals(Set.of(ingredient1, ingredient2), null, null);
        assertEquals(Set.of(meal1, meal2), result);
    }
}
