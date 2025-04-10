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
    private Ingredient ingredient3;
    private Meal meal1;
    private Meal meal2;
    private Meal meal3;

    @BeforeEach
    void setUp() throws IOException {
        mockModel = new RecipeModel();

        ingredient1 = new Ingredient("1", "Chicken", "");
        ingredient2 = new Ingredient("268", "Rice", "");
        ingredient3 = new Ingredient("149", "Garlic", "");

        meal1 = new Meal("Chicken Alfredo Primavera", "https://www.themealdb.com/images/media/meals/syqypv1486981727.jpg", "52796");
        meal2 = new Meal("Chicken Congee", "https://www.themealdb.com/images/media/meals/1529446352.jpg", "52956");
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
        Set<Meal> result = mockModel.processMeals(userIngredients, "Chicken", "Italian");
        System.out.println(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(meal1));
    }

    @Test
    void testProcessMealsWithOnlyOneIngredient() throws IOException {
        Set<Ingredient> userIngredients = Set.of(ingredient2);
        System.out.println(userIngredients);
        Set<Meal> result = mockModel.processMeals(userIngredients, null, null);
        System.out.println(result);
        assertEquals(11, result.size());
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
        Recipe recipe = mockModel.getRecipeByIdMeal(Integer.parseInt(meal1.idMeal()));
        assertNotNull(recipe);
        System.out.println(recipe);
        assertEquals("Chicken Alfredo Primavera", recipe.recipeName());
    }


    // 1. Single ingredient, no category, no area
    @Test
    void testSingleIngredientOnly() throws IOException {
        Set<Meal> result = mockModel.processMeals(Set.of(ingredient1), null, null);
        System.out.println(result);
        assertTrue(result.contains(meal1));
    }

    // 2. Two ingredients, no category, no area
    @Test
    void testTwoIngredientsOnly() throws IOException {
        // ingredient 1: chicken
        // ingredient 2: rice
        // return all meals using chicken OR rice
        Set<Meal> result = mockModel.processMeals(Set.of(ingredient1, ingredient2), null, null);
        System.out.println(result);
        assertEquals(22, result.size());
        assertTrue(result.contains(meal1));
        assertTrue(result.contains(meal2));
    }

    // 3. Single ingredient + one category, no area
    @Test
    void testSingleIngredientAndCategory() throws IOException {
        // ingredient3 = garlic
        Set<Meal> result = mockModel.processMeals(Set.of(ingredient3), "Pasta", null);
        System.out.println(result);
        // Set<Meal> findIntersection(List<Set<Meal>> mealSets)
        String expectedResult = "[Meal[mealName=Lasagne, mealImg=https://www.themealdb.com/images/media/meals/wtsvxx1511296896.jpg, idMeal=52844], Meal[mealName=Pilchard puttanesca, mealImg=https://www.themealdb.com/images/media/meals/vvtvtr1511180578.jpg, idMeal=52837], Meal[mealName=Venetian Duck Ragu, mealImg=https://www.themealdb.com/images/media/meals/qvrwpt1511181864.jpg, idMeal=52838]]";
        Set<Meal> expectedIntersection = mockModel.findIntersection(
                List.of(mockModel.getMealsByIngredient(Set.of(ingredient3)),
                        mockModel.getMealsByCategory("Pasta"))
        );
        assertEquals(expectedResult, result.toString());
        assertEquals(expectedIntersection, result);

    }

    // 4. Two ingredients + one area, no category
    @Test
    void testTwoIngredientsAndArea() throws IOException {
        // ingredient 1: chicken
        // ingredient 2: rice
        // return all meals using chicken OR rice
        Set<Meal> result = mockModel.processMeals(Set.of(ingredient1, ingredient2), null, "Italian");
        Set<Meal> expected = mockModel.findIntersection(
                List.of(mockModel.getMealsByIngredient(Set.of(ingredient1, ingredient2)),
                        mockModel.getMealsByArea("Italian"))
        );
        assertEquals(expected, result);
    }

    // 5. Single ingredient + one category + one area
    @Test
    void testSingleIngredientCategoryArea() throws IOException {
        Set<Meal> result = mockModel.processMeals(Set.of(ingredient1), "Pasta", "Italian");
        // Set<Meal> findIntersection(List<Set<Meal>> mealSets)
        Set<Meal> expected = mockModel.findIntersection(
                List.of(mockModel.getMealsByIngredient(Set.of(ingredient1)),
                        mockModel.getMealsByCategory("Pasta"),  mockModel.getMealsByArea("Italian"))
        );
        assertEquals(expected, result);
    }

    // 6. Two ingredients, no category, no area (same as test 2, included for clarity)
    @Test
    void testTwoIngredientsNoCategoryNoAreaRepeated() throws IOException {
        Set<Meal> result = mockModel.processMeals(Set.of(ingredient1, ingredient2), "Pasta", "Italian");
        // Set<Meal> findIntersection(List<Set<Meal>> mealSets)
        Set<Meal> expected = mockModel.findIntersection(
                List.of(mockModel.getMealsByIngredient(Set.of(ingredient1, ingredient2)),
                        mockModel.getMealsByCategory("Pasta"),  mockModel.getMealsByArea("Italian"))
        );
        assertEquals(expected, result);

    }
}
