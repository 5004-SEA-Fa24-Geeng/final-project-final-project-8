RecipeModelTest
│
├── 🔁 Singleton Logic: Verifies that `getInstance()` always returns the same object
│   └── testSingletonReturnsSameInstance
│
├── 📦 Loading Initial Data: Ensures initial API data (ingredients, categories, areas) is loaded correctly
│   ├── testGetAllIngredients
│   ├── testGetAllCategories
│   └── testGetAllAreas
│
├── ⚙️ Core Logic: Meal Filtering `processMeals()` → Covers all combinations of user input (ingredient, category, area)
│   ├── 1. testSingleIngredientOnly                  [✓ ingredient only]
│   ├── 2. testTwoIngredientsOnly                   [✓ 2 ingredients only]
│   ├── 3. testSingleIngredientAndCategory          [✓ ingredient + category]
│   ├── 4. testTwoIngredientsAndArea                [✓ 2 ingredients + area]
│   ├── 5. testSingleIngredientCategoryArea         [✓ full input: ingredient + category + area]
│   ├── 6. testNoInputs                              [✓ none]
│   ├── 7. testOnlyCategory                          [✓ category only]
│   ├── 8. testOnlyArea                              [✓ area only]
│   └── 9. testOnlyCategoryAndArea                   [✓ category + area]
│
├── 🔗 Set Operations → Verifies internal logic for computing shared meals (intersection)
│   ├── testFindIntersection
│   └── testFindIntersectionWithEmptySet
│
├── 🍽️ Retrieve a Recipe by Meal ID → Confirms ability to retrieve and parse full recipe details by meal ID
│   └── testGetRecipeByIdMeal
│
├── 🛡️ Exception Handling in API Calls → Simulates API failures and ensures the model safely returns empty results
│   ├── testGetMealsByIngredient_whenIOException_thenReturnsEmptySet
│   ├── testGetMealsByCategory_whenIOException_thenReturnsEmptySet
│   └── testGetMealsByArea_whenIOException_thenReturnsEmptySet
│
├── 🧠 Caching Behavior → Ensures that API calls are cached and not repeated for repeated inputs
│   ├── testIngredientCaching
│   ├── testCategoryCaching
│   └── testAreaCaching
│       
