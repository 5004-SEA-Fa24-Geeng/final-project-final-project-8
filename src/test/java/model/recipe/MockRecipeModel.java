package model.recipe;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MockRecipeModel implements IRecipeModel {

    private final Set<Ingredient> mockIngredients = Set.of(
            new Ingredient("1", "Chicken", ""),
            new Ingredient("2", "Rice", "")
    );

    private final Set<String> mockCategories = Set.of("Main", "Dessert");
    private final Set<String> mockAreas = Set.of("Italian", "Chinese");

    private Set<Ingredient> userIngredients;
    private String userCategory;
    private String userArea;

    private final List<Observer> observers = new ArrayList<>();

    private final Meal meal1 = new Meal("Chicken Stew", "thumb1", "101");
    private final Meal meal2 = new Meal("Chicken Rice", "thumb2", "102");
    private final Meal meal3 = new Meal("Pasta", "thumb3", "103");

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public Set<Ingredient> getAllIngredients() {
        return mockIngredients;
    }

    @Override
    public Set<String> getAllCategories() {
        return mockCategories;
    }

    @Override
    public Set<String> getAllAreas() {
        return mockAreas;
    }

    @Override
    public Set<Meal> processMeals(Set<Ingredient> userIngredients, String category, String area) {
        setUserIngredients(userIngredients);
        setUserCategory(category);
        setUserArea(area);

        List<Set<Meal>> mealSets = new ArrayList<>();

        if (userIngredients != null) mealSets.add(getMealsByIngredient(userIngredients));
        if (category != null) mealSets.add(getMealsByCategory(category));
        if (area != null) mealSets.add(getMealsByArea(area));

        return switch (mealSets.size()) {
            case 0 -> Set.of();
            case 1 -> mealSets.get(0);
            default -> findIntersection(mealSets);
        };
    }

    @Override
    public Set<Meal> findIntersection(List<Set<Meal>> mealSets) {
        if (mealSets.isEmpty()) return Set.of();
        Set<Meal> result = new HashSet<>(mealSets.get(0));
        for (int i = 1; i < mealSets.size(); i++) {
            result.retainAll(mealSets.get(i));
        }
        return result;
    }

    @Override
    public void setUserIngredients(Set<Ingredient> userIngredients) {
        this.userIngredients = userIngredients;
        notifyObservers();
    }

    @Override
    public void setUserCategory(String category) {
        this.userCategory = category;
        notifyObservers();
    }

    @Override
    public void setUserArea(String area) {
        this.userArea = area;
        notifyObservers();
    }

    @Override
    public Set<Meal> getMealsByIngredient(Set<Ingredient> ingredients) {
        Set<Meal> result = new HashSet<>();
        for (Ingredient i : ingredients) {
            if (i.nameIngredient().equals("Chicken")) result.add(meal1);
            if (i.nameIngredient().equals("Rice")) result.add(meal2);
        }
        return result;
    }

    @Override
    public Set<Meal> getMealsByCategory(String category) {
        return switch (category) {
            case "Main" -> Set.of(meal1, meal3);
            case "Dessert" -> Set.of();
            default -> Set.of();
        };
    }

    @Override
    public Set<Meal> getMealsByArea(String area) {
        return switch (area) {
            case "Italian" -> Set.of(meal1, meal3);
            case "Chinese" -> Set.of(meal2);
            default -> Set.of();
        };
    }

    @Override
    public Set<Meal> getMutualMeals(Set<Meal> s1, Set<Meal> s2, Set<Meal> s3) {
        return s1.stream().filter(s2::contains).filter(s3::contains).collect(Collectors.toSet());
    }

    @Override
    public Recipe getRecipeByIdMeal(int idMeal) throws IOException {
        return new Recipe(
                idMeal,
                "Mock Recipe",
                "Main",
                "Italian",
                "Cook it well.",
                "https://youtube.com/mock",
                "https://image.com/mock.jpg",
                List.of("Chicken", "Salt"),
                List.of("200g", "1 tsp")
        );
    }
}
