package model.api;

import model.formatter.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

/**
 * Utility class for interacting with the MealDB API. (API link: "https://www.themealdb.com/api.php")
 * Provides methods to fetch meal-related data such as ingredients, categories, areas, meal IDs, and recipes
 * from the API. The data is returned in the form of JSON responses, which can be parsed and processed.
 */
public final class ApiUtils {

    // URL to get all ingredient/category/area information depending on the code provided.
    private static final String ALL_LIST = "https://www.themealdb.com/api/json/v1/1/list.php?%s=list";
    // URL to search meals by ingredient. Use this link to get idMeal.
    private static final String GET_IDMEAL = "https://www.themealdb.com/api/json/v1/1/filter.php?i=%s";
    // URL to search recipe by idMeal.
    private static final String GET_RECIPE = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=%d";

    // Private constructor to prevent instantiation
    private ApiUtils() {
        // Prevent instantiation
    }


    /**
     * Fetches all available ingredients from the MealDB API.
     *
     * @return an input stream containing the JSON data for all ingredients
     */
    public static InputStream getAllIngredients() {
        String code = "i";
        String allIngredientUrl = String.format(ALL_LIST, code);
        return getUrlContents(allIngredientUrl);
    }

    /**
     * Fetches all available categories from the MealDB API.
     *
     * @return an input stream containing the JSON data for all categories
     */
    public static InputStream getAllCategories() {
        String code = "c";
        String allCategoriesUrl = String.format(ALL_LIST, code);
        return getUrlContents(allCategoriesUrl);
    }

    /**
     * Fetches all available areas from the MealDB API.
     *
     * @return an input stream containing the JSON data for all areas
     */
    public static InputStream getAllAreas() {
        String code = "a";
        String allAreasUrl = String.format(ALL_LIST, code);
        return getUrlContents(allAreasUrl);
    }

    /**
     * Fetches idMeal associated with the provided ingredient name from the MealDB API.
     *
     * @param ingredientName the name of the ingredient to search for
     * @return a set of meal IDs that include the specified ingredient
     * @throws IOException if an error occurs while retrieving or processing the data
     */
    public static Set<Integer> getIdMealByIngredient(String ingredientName) throws IOException {
        String ingredientUrl = String.format(GET_IDMEAL, castIngredientName(ingredientName));
        InputStream data = getUrlContents(ingredientUrl);
        return JsonParser.extractIdMeal(data);
    }

    /**
     * Converts an ingredient name to a valid format for the API request.
     * Replaces spaces with underscores and converts to lowercase. e.g. Chicken Breast -> chicken_breast
     *
     * @param ingredientName the name of the ingredient to be formatted
     * @return the formatted ingredient name suitable for the API
     */
    public static String castIngredientName(String ingredientName) {
        return ingredientName.replace(" ", "_").toLowerCase();
    }

    /**
     * Fetches a recipe by its idMeal from the MealDB API.
     *
     * @param idMeal the meal ID of the recipe to retrieve
     * @return an input stream containing the JSON data for the recipe
     */
    public static InputStream getRecipeByIdMeal(int idMeal) {
        String url = String.format(GET_RECIPE, idMeal);
        return getUrlContents(url);
    }

    /**
     * Fetches the contents from the provided URL as an InputStream.
     *
     * @param urlStr the URL to fetch data from
     * @return an input stream containing the data from the URL, or an empty input stream in case of failure
     */

    public static InputStream getUrlContents(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            int status = con.getResponseCode();
            if (status == 200) {
                return con.getInputStream();
            } else {
                System.err.println("Failed to connect to " + urlStr);
            }
        } catch (Exception ex) {
            System.err.println("Failed to connect to " + urlStr);
        }
        return InputStream.nullInputStream();
    }
}
