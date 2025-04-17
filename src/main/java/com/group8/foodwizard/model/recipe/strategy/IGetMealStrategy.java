package com.group8.foodwizard.model.recipe.strategy;

import com.group8.foodwizard.model.recipe.Meal;

import java.util.Set;

/**
 * Strategy interface for retrieving meals based on specific criteria,
 * such as ingredient, category, or area.
 *
 * Implementing classes define their logic for fetching meals,
 * which is using a caching mechanism to reduce API calls.
 */
public interface IGetMealStrategy {

    /**
     * Gets a set of meals that match the strategy's criteria.
     *
     * @return a set of {@link Meal} objects based on the strategy
     */
    Set<Meal> getMeals();
}
