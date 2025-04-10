import { useIngredientStore } from '@/store/ingredientStore'
import { RecipePreviewCard } from './RecipePreviewCard'
import { Button } from '../ui/button'
import { Search } from 'lucide-react'
import { RecipePreviewCardSkeleton } from './RecipePreviewCardSkeleton'

export function RecipePreviewGridCard() {
  const { recipePreviews, loadingRecipePreviews } = useIngredientStore()

  if (loadingRecipePreviews) {
    return (
      <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-4">
        {Array.from({ length: 5 }, (_, index) => (
          <div key={index} className="max-w-sm w-full">
            <RecipePreviewCardSkeleton />
          </div>
        ))}
      </div>
    )
  }

  return (
    <>
      {recipePreviews.length === 0 ? (
        <div className="text-center p-4 text-gray-500">
          No recipes found. Press{' '}
          <Button size="icon" variant="outline" className="size-6">
            <Search />
          </Button>{' '}
          to find recipes.
        </div>
      ) : (
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-4">
          {recipePreviews.map((recipePreview) => (
            <div key={recipePreview.idMeal} className="max-w-sm w-full">
              <RecipePreviewCard {...recipePreview} />
            </div>
          ))}
        </div>
      )}
    </>
  )
}
