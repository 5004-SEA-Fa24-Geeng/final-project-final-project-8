package com.group8.foodwizard.model.recipe.strategy;

import com.group8.foodwizard.model.recipe.Meal;

import java.util.Set;

public interface IGetMealStrategy {
    Set<Meal> getMeals();
}
