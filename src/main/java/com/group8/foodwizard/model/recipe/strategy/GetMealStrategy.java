package com.group8.foodwizard.model.recipe.strategy;

import com.group8.foodwizard.model.recipe.Meal;
import com.group8.foodwizard.model.recipe.RecipeModel.CachedMealFetcher;

import java.util.Set;

/**
 * Abstract base class for meal retrieval strategies.
 * Implements the {@link IGetMealStrategy} interface and defines the common caching logic
 * using {@link CachedMealFetcher}.
 *
 * Subclasses must implement the {@link #getMeals()} method to define their specific retrieval logic.
 */
public abstract class GetMealStrategy implements IGetMealStrategy {

    /** Cached fetcher used to retrieve meals and reduce API calls. */
    private final CachedMealFetcher fetcher;

    /**
     * Constructs a new GetMealStrategy with the given cached fetcher.
     *
     * @param fetcher the fetcher to fetch the cached data
     */
    public GetMealStrategy(CachedMealFetcher fetcher) {
        this.fetcher = fetcher;
    }

    /**
     * Retrieves a set of meals based on the strategy's implementation.
     *
     * @return a set of meals matching the criteria defined in the concrete strategy
     */
    @Override
    public abstract Set<Meal> getMeals();

    /**
     * Returns the cached meal fetcher used by this strategy.
     *
     * @return the {@link CachedMealFetcher} instance used to fetch meals with caching
     */
    public CachedMealFetcher getFetcher() {
        return fetcher;
    }
}
