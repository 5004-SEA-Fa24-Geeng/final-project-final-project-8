import { AddIngredientButton } from '@/components/custom/addIngredientButton'
import { IngredientGridCard } from '@/components/custom/ingredientGridCard'
import { FilterRegionDropdown } from '@/components/custom/filterRegionDropdown'
import { FilterCategoryDropdown } from '@/components/custom/filterCategoryDropdown'
import { SearchRecipeButton } from '@/components/custom/searchRecipeButton'
import { RecipePreviewGridCard } from '@/components/custom/RecipePreviewGridCard'

function HomePage() {
  return (
    <div className="flex flex-col w-auto m-2 sm:m-4 p-4 rounded-lg shadow-sm border border-gray-200 gap-4">
      <div>
        <div className="flex items-center space-x-2 gap-1">
          <div className="font-bold text-2xl">Ingredients</div>
          <AddIngredientButton />
          <FilterRegionDropdown />
          <FilterCategoryDropdown />
          <SearchRecipeButton />
        </div>
        <hr className="border-black my-2" />
        <IngredientGridCard></IngredientGridCard>
      </div>
      <div>
        <div className="py-1 font-bold text-2xl">Recipes</div>
        <hr className="border-black my-2" />
        <RecipePreviewGridCard />
      </div>
    </div>
  )
}

export default HomePage
