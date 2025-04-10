import axios from "axios";
import { Ingredient } from "../models/ingredientModel";

const baseUrl =
    "http://localhost:8080";

const getAllIngredients = async () => {
    try {
        const response = await axios.get(`${baseUrl}/api/ingredients`);
        return response.data;
    } catch (error) {
        console.log(error);
        return [];
    }
};

const getAllCategories = async () => {
    try {
        const response = await axios.get(`${baseUrl}/api/categories`);
        return response.data;
    }
    catch (error) {
        console.log(error);
        return [];
    }
};

const getAllRegions = async () => {
    try {
        const response = await axios.get(`${baseUrl}/api/regions`);
        return response.data;
    }
    catch (error) {
        console.log(error);
        return [];
    }
};

interface RecipePreviewRequest {
    ingredients: Ingredient[];
    category?: string | null;
    region?: string | null;
}

const getRecipePreviews = async (request: RecipePreviewRequest) => {
    try {
        const response = await axios.post(`${baseUrl}/api/getRecipePreviews`, {
            ingredients: request.ingredients,
            category: request.category || null,
            region: request.region || null
        });
        return response.data;
    }
    catch (error) {
        console.log('Error fetching recipe previews:', error);
        return [];
    }
};

const getRecipe = async (id: string) => {
    try {
        const response = await axios.get(`${baseUrl}/api/recipe/${id}`);
        return response.data;
    } catch (error) {
        console.log('Error fetching recipe:', error);
        return null;
    }
}

export default { getAllIngredients, getAllCategories, getAllRegions, getRecipePreviews, getRecipe };