package model.recipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeModelTest {

    private IRecipeModel model;

    private final Ingredient lime = new Ingredient("202", "Lime", "www.themealdb.com/images/ingredients/lime-medium.png");
    private final Meal mockMeal = new Meal(
            "Chick-Fil-A Sandwich",
            "https://www.themealdb.com/images/media/meals/sbx7n71587673021.jpg",
            "53016"
    );

    @BeforeEach
    void setUp() {
        model = new MockRecipeModel();
    }

    @Test
    void getAllIngredients() {
        Set<Ingredient> ingredients = model.getAllIngredients();
        assertEquals(1, ingredients.size());
        assertTrue(ingredients.contains(lime));
    }

    @Test
    void getAllCategories() {
        Set<String> categories = model.getAllCategories();
        assertTrue(categories.contains("Chicken"));
        assertTrue(categories.contains("Beef"));
    }

    @Test
    void getAllAreas() {
        Set<String> areas = model.getAllAreas();
        assertTrue(areas.contains("American"));
        assertTrue(areas.contains("British"));
    }

    @Test
    void findIntersection() {
        Set<Meal> set1 = Set.of(mockMeal);
        Set<Meal> set2 = Set.of(mockMeal);
        Set<Meal> set3 = Set.of(mockMeal);
        Set<Meal> intersection = model.findIntersection(List.of(set1, set2, set3));
        assertEquals(1, intersection.size());
        assertTrue(intersection.contains(mockMeal));
    }

    @Test
    void setUserIngredients() throws IOException {
        model.setUserIngredients(Set.of(lime));
        Set<Meal> result = model.getMealsByIngredient(Set.of(lime));
        assertTrue(result.contains(mockMeal));
    }

    @Test
    void setUserCategory() throws IOException {
        model.setUserCategory("Chicken");
        Set<Meal> result = model.getMealsByCategory("Chicken");
        assertTrue(result.contains(mockMeal));
    }

    @Test
    void setUserArea() throws IOException {
        model.setUserArea("American");
        Set<Meal> result = model.getMealsByArea("American");
        assertTrue(result.contains(mockMeal));
    }

    @Test
    void getMealsByIngredient() throws IOException {
        Set<Meal> result = model.getMealsByIngredient(Set.of(lime));
        assertEquals(1, result.size());
        assertTrue(result.contains(mockMeal));
    }

    @Test
    void getMealsByCategory() throws IOException {
        Set<Meal> result = model.getMealsByCategory("Chicken");
        assertEquals(1, result.size());
        assertTrue(result.contains(mockMeal));
    }

    @Test
    void getMealsByArea() throws IOException {
        Set<Meal> result = model.getMealsByArea("American");
        assertEquals(1, result.size());
        assertTrue(result.contains(mockMeal));
    }

    @Test
    void getMutualMeals() {
        Set<Meal> meals1 = Set.of(mockMeal);
        Set<Meal> meals2 = Set.of(mockMeal);
        Set<Meal> meals3 = Set.of(mockMeal);
        Set<Meal> result = model.getMutualMeals(meals1, meals2, meals3);
        assertEquals(1, result.size());
        assertTrue(result.contains(mockMeal));
    }

    @Test
    void getRecipeByIdMeal() throws IOException {
        Recipe recipe = model.getRecipeByIdMeal(53016);
        assertNotNull(recipe);
        assertEquals("Chick-Fil-A Sandwich", recipe.recipeName());
        assertEquals("Chicken", recipe.category());
        assertTrue(recipe.ingredients().contains("Lime"));
    }

    @Test
    void processMeals_withLimeChickenAmerican() throws IOException {
        Set<Meal> result = model.processMeals(Set.of(lime), "Chicken", "American");
        assertEquals(1, result.size());
        assertTrue(result.contains(mockMeal));
    }
}
