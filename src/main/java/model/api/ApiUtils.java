package model.api;

import model.formatter.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

public final class ApiUtils {
    private static final String ALL_LIST = "https://www.themealdb.com/api/json/v1/1/list.php?%s=list";
    private static final String GET_IDMEAL = "https://www.themealdb.com/api/json/v1/1/filter.php?i=%s";
    private static final String GET_RECIPE = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=%d";

    private ApiUtils() {
        // Prevent instantiation
    }

    public static InputStream getAllIngredients() {
        String code = "i";
        String allIngredientUrl = String.format(ALL_LIST, code);
        return getUrlContents(allIngredientUrl);
    }

    public static InputStream getAllCategories() {
        String code = "c";
        String allCategoriesUrl = String.format(ALL_LIST, code);
        return getUrlContents(allCategoriesUrl);
    }

    public static InputStream getAllAreas() {
        String code = "a";
        String allAreasUrl = String.format(ALL_LIST, code);
        return getUrlContents(allAreasUrl);
    }

    public static Set<Integer> getIdMealByIngredient(String ingredientName) throws IOException {
        String ingredientUrl = String.format(GET_IDMEAL, ingredientName);
        InputStream data = getUrlContents(ingredientUrl);
        return JsonParser.extractIdMeal(data);
    }

    public static InputStream getRecipeByIdMeal(int idMeal) {
        String url = String.format(GET_RECIPE, idMeal);
        return getUrlContents(url);
    }

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
