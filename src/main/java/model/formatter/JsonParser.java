package model.formatter;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.api.ApiUtils;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class JsonParser {
    private static final int MAX_INGREDIENT_COUNT = 20;
    private static final String INGREDIENT_IMAGE = "www.themealdb.com/images/ingredients/%s-medium.png";

    private JsonParser() {
        //empty
    }

    public static Set<Integer> extractIdMeal(InputStream inputStream) throws IOException {
        Set<Integer> mealIds = new HashSet<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(inputStream);
        JsonNode mealsNode = rootNode.path("meals");
        if (mealsNode.isArray() && mealsNode.size() > 0) {
            for (JsonNode meal : mealsNode) {
                int id = meal.path("idMeal").asInt();
                mealIds.add(id);
            }
        }
        return mealIds;
    }


    public static Map<String, Object> extractRecipeData(InputStream input) throws IOException {
        Map<String, Object> recipeData = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(input);
        JsonNode recipeNode = rootNode.path("meals");
        if (recipeNode.isArray() && recipeNode.size() > 0) {
            JsonNode meal = recipeNode.get(0);
            recipeData.put("recipeId", meal.path("idMeal").asInt());
            recipeData.put("mealName", meal.path("strMeal").asText());
            recipeData.put("category", meal.path("strCategory").asText());
            recipeData.put("area", meal.path("strArea").asText());
            recipeData.put("image", meal.path("strMealThumb").asText());
            recipeData.put("youtube", meal.path("strYoutube").asText());
            recipeData.put("instructions", meal.path("strInstructions").asText());

            List<String> ingredients = new ArrayList<>();
            List<String> measures = new ArrayList<>();
            for (int i = 1; i <= MAX_INGREDIENT_COUNT; i++) {
                String ingredient = meal.path("strIngredient" + i).asText();
                String measure = meal.path("strMeasure" + i).asText();

                if (ingredient != null && !ingredient.isEmpty() && !ingredient.equals("null")) {
                    ingredients.add(ingredient);
                    measures.add(measure);
                }
            }

            recipeData.put("ingredientsList", ingredients);
            recipeData.put("measuresList", measures);
        }
        return recipeData;
    }

    public static Recipe mapToRecipe(Map<String, Object> recipeData) {
        int recipeId = (int) recipeData.get("recipeId");
        String recipeName = (String) recipeData.get("recipeName");
        String category = (String) recipeData.get("category");
        String area = (String) recipeData.get("area");
        String instructions = (String) recipeData.get("instructions");
        String youtube = (String) recipeData.get("youtube");
        String image = (String) recipeData.get("image");
        List<String> ingredients = (List<String>) recipeData.get("ingredientsList");
        List<String> measures = (List<String>) recipeData.get("measuresList");

        return new Recipe(recipeId, recipeName, instructions, image, youtube, category, area, ingredients, measures);
    }

    public static Set<Ingredient> allIngredientsList(InputStream input) throws IOException {
        // Ingredient(int idIngredient, String strIngredient, String strImage)
        Set<Ingredient> allIngredients = new HashSet<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(input);
        JsonNode mealsNode = rootNode.path("meals");
        if (mealsNode.isArray() && mealsNode.size() > 0) {
            for (JsonNode ingredient : mealsNode) {
                String ingredientId = ingredient.path("idIngredient").asText();
                String ingredientName = ingredient.path("strIngredient").asText();
                String imageUrl = String.format(INGREDIENT_IMAGE, ApiUtils.castIngredientName(ingredientName));
                Ingredient singleIngredient = Ingredient(ingredientId, ingredientName, imageUrl);
                allIngredients.add(singleIngredient);
            }
        }
        return allIngredients;
    }

    public static Set<String> allAreasList(InputStream input) throws IOException {
//        InputStream input = ApiUtils.getAllAreas();
        Set<String> allAreas = new HashSet<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(input);
        JsonNode mealsNode = rootNode.path("meals");
        if (mealsNode.isArray() && mealsNode.size() > 0) {
            for (JsonNode area : mealsNode) {
                allAreas.add(area.path("strArea").asText());
            }
        }
        return allAreas;
    }

    public static Set<String> allCategoriesList(InputStream input) throws IOException {
//       InputStream input = ApiUtils.getAllCategories();
       Set<String> allCategories = new HashSet<>();
       ObjectMapper mapper = new ObjectMapper();
       JsonNode rootNode = mapper.readTree(input);
       JsonNode mealsNode = rootNode.path("meals");
       if (mealsNode.isArray() && mealsNode.size() > 0) {
           for (JsonNode category : mealsNode) {
               allCategories.add(category.path("strCategory").asText());
           }
       }
       return allCategories;
    }
}
