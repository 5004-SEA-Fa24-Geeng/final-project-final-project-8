package model.recipe;

import java.util.Set;

public interface MealFilterStrategy {
    Set<Meal> filter() throws Exception;
}
