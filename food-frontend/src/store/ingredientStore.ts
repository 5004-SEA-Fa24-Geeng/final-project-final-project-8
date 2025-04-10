import { create } from 'zustand';
import { Ingredient } from '../models/ingredientModel';
import ingredientService from '../services/ingredientService';
import { RecipePreview } from '../models/recipePreviewModel';

interface IngredientState {
    availableIngredients: Ingredient[];
    setAvailableIngredients: (ingredients: Ingredient[]) => void;
    userIngredients: Ingredient[];
    setUserIngredients: (ingredients: Ingredient[]) => void;
    addUserIngredient: (ingredient: Ingredient) => void;
    removeUserIngredient: (ingredient: Ingredient) => void;
    loadingRecipePreviews: boolean;
    filterArea: string | null;
    setFilterRegion: (area: string | null) => void;
    filterCategory: string | null;
    setFilterCategory: (category: string | null) => void;
    clearFilters: () => void;
    availableRegions: string[];
    setAvailableRegions: (areas: string[]) => void;
    availableCategories: string[];
    setAvailableCategories: (categories: string[]) => void;
    recipePreviews: RecipePreview[];
    setRecipePreviews: (recipes: RecipePreview[]) => void;
}

export const useIngredientStore = create<IngredientState>()((set) => ({
    availableIngredients: [],
    setAvailableIngredients: (ingredients) => set({ availableIngredients: ingredients }),
    userIngredients: [],
    setUserIngredients: (ingredients) => set({ userIngredients: ingredients }),
    addUserIngredient: (ingredient) => set((state) => {
        const isAlreadyAdded = state.userIngredients.some(
            (item) => item.idIngredient === ingredient.idIngredient
        );

        if (isAlreadyAdded) {
            return state;
        }

        const updatedUserIngredients = [...state.userIngredients, ingredient];

        // Save to localStorage
        // localStorage.setItem('userIngredients', JSON.stringify(updatedUserIngredients));

        // Return the updated state
        return {
            userIngredients: updatedUserIngredients,
        };
    }),
    removeUserIngredient: (ingredient) => set((state) => {
        const updatedUserIngredients = state.userIngredients.filter(
            (item) => item.idIngredient !== ingredient.idIngredient
        );
        // localStorage.setItem('userIngredients', JSON.stringify(updatedUserIngredients));
        return { userIngredients: updatedUserIngredients };
    }
    ),
    loadingRecipePreviews: false,
    filterArea: null,
    filterCategory: null,
    setFilterRegion: (area) => set({ filterArea: area }),
    setFilterCategory: (category) => set({ filterCategory: category }),
    clearFilters: () => set({ filterArea: null, filterCategory: null }),
    availableRegions: [],
    setAvailableRegions: (areas) => set({ availableRegions: areas }),
    availableCategories: [],
    setAvailableCategories: (categories) => set({ availableCategories: categories }),
    recipePreviews: [],
    setRecipePreviews: (recipes) => set({ recipePreviews: recipes }),
}));

export const fetchIngredients = async () => {
    try {
        const response = await ingredientService.getAllIngredients();
        const ingredients = response.map((item: Ingredient) => ({
            idIngredient: item.idIngredient,
            nameIngredient: item.nameIngredient,
            strImage: item.strImage
        }));
        return ingredients;
    } catch (error) {
        console.error('Failed to fetch ingredients:', error);
        return [];
    }
}

export const fetchAvailableRegions = async () => {
    try {
        const regions = await ingredientService.getAllRegions();
        if (Array.isArray(regions)) {
            useIngredientStore.getState().setAvailableRegions(regions);
            return regions.sort((a, b) => a.localeCompare(b));
        }
        return [];
    } catch (error) {
        console.error('Failed to fetch regions:', error);
        return [];
    }
};

export const fetchAvailableCategories = async () => {
    try {
        const categories = await ingredientService.getAllCategories();
        if (Array.isArray(categories)) {
            useIngredientStore.getState().setAvailableCategories(categories);
            return categories.sort((a, b) => a.localeCompare(b));
        }
        return [];
    } catch (error) {
        console.error('Failed to fetch categories:', error);
        return [];
    }
};

export const fetchRecipePreviews = async () => {
    const { userIngredients, filterArea, filterCategory } = useIngredientStore.getState();
    if (userIngredients.length === 0) {
        console.log('No ingredients selected');
        return [];
    }
    try {
        useIngredientStore.setState({ loadingRecipePreviews: true });

        // Send request to the backend with selected ingredients and filters
        const response: RecipePreview[] = await ingredientService.getRecipePreviews({
            ingredients: userIngredients,
            category: filterCategory,
            region: filterArea
        });
        console.log('Fetched recipe previews:', response);
        // Store the response in the state
        useIngredientStore.getState().setRecipePreviews(response);
        useIngredientStore.setState({ loadingRecipePreviews: false });

        return response;
    } catch (error) {
        console.error('Failed to fetch recipe previews:', error);
        useIngredientStore.setState({ loadingRecipePreviews: false });
        return [];
    }
};

export const initializeIngredients = async () => {
    const fetchedIngredients = await fetchIngredients();
    useIngredientStore.setState({ availableIngredients: fetchedIngredients });
    // const storedIngredients = localStorage.getItem('availableIngredients');
    // const storedUserIngredients = localStorage.getItem('userIngredients');
    // if (storedUserIngredients) {
    //     useIngredientStore.setState({ userIngredients: JSON.parse(storedUserIngredients) });
    // }
    // if (storedIngredients) {
    //     useIngredientStore.setState({ availableIngredients: JSON.parse(storedIngredients) });
    // } else {
    //     useIngredientStore.setState({ loading: true });
    //     try {
    //         const response = await ingredientService.getAllIngredients();

    //         // Set the ingredients in the store
    //         useIngredientStore.setState({
    //             availableIngredients: fetchedIngredients,
    //             loading: false
    //         });

    //         // Save to localStorage for future use
    //         localStorage.setItem('ingredients', JSON.stringify(fetchedIngredients));
    //     } catch (error) {
    //         console.error('Failed to fetch ingredients:', error);
    //         useIngredientStore.setState({ loading: false });
    //     }
    // }
};

export const initializeRegions = async () => {
    const regions = await fetchAvailableRegions();
    useIngredientStore.setState({ availableRegions: regions });
}

export const initializeCategories = async () => {
    const categories = await fetchAvailableCategories();
    useIngredientStore.setState({ availableCategories: categories });
}