import { Button } from '../ui/button'
import { Search } from 'lucide-react'
import { RecipePreview } from '@/models/recipePreviewModel'
import {
  useIngredientStore,
  fetchRecipePreviews,
} from '@/store/ingredientStore'

export function SearchRecipeButton() {
  const { setRecipePreviews } = useIngredientStore()

  const handleSearch = async () => {
    const recipes: RecipePreview[] = await fetchRecipePreviews()
    setRecipePreviews(recipes)
  }

  return (
    <Button
      size="icon"
      variant="outline"
      className="size-9"
      onClick={handleSearch}
    >
      <Search />
    </Button>
  )
}
