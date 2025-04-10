'use client'
import { Ingredient } from '@/models/ingredientModel'
import { Trash2 } from 'lucide-react'
import { Button } from '../ui/button'
import { useIngredientStore } from '@/store/ingredientStore'

export function IngredientCard(ingredient: Ingredient) {
  return (
    <div className="w-full rounded-lg shadow-sm border border-gray-200 overflow-hidden">
      <img
        src={`https://${ingredient.strImage}`}
        alt={ingredient.nameIngredient}
        className="w-full aspect-square"
      ></img>
      <div className="flex justify-between p-1 m-1">
        <h1
          className="py-1 font-bold text-lg md:text-xl truncate"
          title={ingredient.nameIngredient}
        >
          {ingredient.nameIngredient}
        </h1>
        <Button
          variant="outline"
          size="icon"
          className="hover:bg-red-500/80 transition-colors"
          onClick={() => {
            useIngredientStore.getState().removeUserIngredient(ingredient)
          }}
        >
          <Trash2 />
        </Button>
      </div>
    </div>
  )
}
