# Design Documents

You may have multiple design documents for this project. Place them all in this folder. File naming is up to you, but it should be clear what the document is about. At the bare minimum, you will want a pre/post UML diagram for the project. 

### Full project details - [Project Document (Google Doc)](https://docs.google.com/document/d/1VaDUGM-LZnDLe40iMbah4sar-XQG5n3iLFPSMUdu374/edit?usp=sharing)

## Initial UML
<img src='img/initial_uml.png' title='initial uml' alt='initial uml' />


## Final UML
```mermaid
classDiagram

%% Dependencies
JsonParser --> ApiUtils : uses
JsonParser --> Recipe : creates
JsonParser --> Ingredient : creates

IRecipeModel <-- RecipeModel  : implements
RecipeModel --> JsonParser : uses
RecipeModel --> Recipe : creates
RecipeModel --> Meal : creates
RecipeModel --> Ingredient : creates
RecipeModel --> CachedMealFetcher : uses

RecipeModel --> Observer : defines
CachedMealFetcher --> ApiUtils : uses
RecipeModel --> GetMealByIngredient : uses
RecipeModel --> GetMealByCategory : uses
RecipeModel --> GetMealByArea : uses

GetMealStrategy --> IGetMealStrategy : implements
GetMealByIngredient --> CachedMealFetcher : uses
GetMealByIngredient --> GetMealStrategy : extends
GetMealByCategory --> CachedMealFetcher : uses
GetMealByCategory --> GetMealStrategy : extends
GetMealByArea --> CachedMealFetcher : uses
GetMealByArea --> GetMealStrategy : extends

searchRecipeApp --> Controller : uses
Controller --> RecipeModel : uses

%% Interfaces
class IRecipeModel {
    <<interface>>
}

class IGetMealStrategy {
    <<interface>>
    + getMeals() Set~Meal~
}

class Observer {
    <<interface>>
    + update() : void
}

%% Abstract class
class GetMealStrategy {
    <<Abstract>>
    * getMeals() Set~Meal~
}

%% Core classes
class ApiUtils {
    + castIngredientName(String) : String
    + getAllIngredients() : InputStream
    + getAllCategories() : InputStream
    + getAllAreas() : InputStream
    + getIdMealByIngredient(String) : Set~Integer~
    + getRecipeByIdMeal(int) : InputStream
    + getUrlContents(String) : InputStream
}

class JsonParser {
    + extractIdMeal(InputStream) : Set~Integer~
    + extractRecipeData(InputStream) : Map~String, Object~
    + mapToRecipe(Map~String, Object~) : Recipe
    + allIngredientsList(InputStream) : Set~Ingredient~
    + allAreasList(InputStream) : Set~String~
    + allCategoriesList(InputStream) : Set~String~
}

class Ingredient {
    <<record>>
    + idIngredient : String
    + strIngredient : String
    + strImage : String
}

class Meal {
    <<record>>
    + mealName : String
    + mealImg : String
    + idMeal : String
}

class Recipe {
    <<record>>
    + recipeId : int
    + recipeName : String
    + category : String
    + area : String
    + instructions : String
    + youtube : String
    + image : String
    + ingredients : List~String~
    + measures : List~String~
}

class RecipeModel {
    - allIngredients : Set~Ingredient~
    - allCategories : Set~String~
    - allAreas : Set~String~
    - userIngredients : Set~Ingredient~
    - userCategory : String
    - userArea : String
    - cachedMealFetcher : CachedMealFetcher

    + getInstance() : RecipeModel
    + addObserver(Observer) : void
    + notifyObservers() : void
    + processMeals(Set~Ingredient~, String, String) : Set~Meal~
    + setUserIngredients(Set~Ingredient~) : void
    + setUserCategory(String) : void
    + setUserArea(String) : void
    + getMealsByIngredient(Set~Ingredient~) : Set~Meal~
    + getMealsByCategory(String) : Set~Meal~
    + getMealsByArea(String) : Set~Meal~
    + findIntersection(List~Set~<Meal~>) : Set~Meal~
    + getRecipeByIdMeal(int) : Recipe
}

class CachedMealFetcher {
    - ingredientCache : Map~String, Set~Meal~~
    - categoryCache : Map~String, Set~Meal~~
    - areaCache : Map~String, Set~Meal~~

    + getMealsByIngredient(Set~Ingredient~) : Set~Meal~
    + getMealsByCategory(String) : Set~Meal~
    + getMealsByArea(String) : Set~Meal~
}

class Controller {
    + processUserIngredients() : void
    + processUserCategory() : void
    + processUserArea() : void
    + processUserMeal() : void
}

class searchRecipeApp {
    + main(String[] args) : void
}

class GetMealByIngredient {
    + getMeals() : Set~Meal~
}

class GetMealByCategory {
    + getMeals() : Set~Meal~
}

class GetMealByArea {
    + getMeals() : Set~Meal~
}

```


## Program Flow
![Food Wizard App Flow Chart](Food%20Wizard%20App%20Flow%20Chart_.png)

### User Interaction:
- Display a comprehensive dropdown list of ingredients in alphabetical order.
- Allow users to select multiple ingredients from the list.

### Backend Processing:
- Fetch all available ingredients from TheMealDB API.
- For each selected ingredient, retrieve corresponding meal IDs.
- Obtain detailed recipes using the retrieved meal IDs.
- Aggregate and filter recipes based on user-selected ingredients.

### Output:
- Display matching recipes with images and brief descriptions.
- Provide detailed views with instructions, ingredient lists, and video tutorials.
- Offer an option to download selected recipes in JSON format.

## MVC
- **[package]** Model
- **[package]** api
    - **[class]** ApiUtils
- **[package]** formatter
    - **[class]** JsonParser
- **[package]** recipe
    - **[interface]** IRecipeModel (Polymorphism)
    - **[class]** RecipeModel
    - **[record class]** Recipe
    - **[record class]** Ingredient
    - **[record class]** Meal
    - **[interface]** Observer (Design pattern: Observer Pattern)
- **[package]** strategy (Design pattern: Strategy Pattern)
    - **[interface]** IGetMealStrategy (Inheritance)
    - **[abstract class]** GetMealStrategy
    - **[class]** GetMealByIngredient
    - **[class]** GetMealByCategory
    - **[class]** GetMealByArea
- **[package]** View
- **[package]** Controller
