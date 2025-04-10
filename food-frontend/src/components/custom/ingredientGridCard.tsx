'use client'

import { IngredientCard } from './ingredientCard'
import { Button } from '../ui/button'
import { Plus } from 'lucide-react'
import { useIngredientStore } from '@/store/ingredientStore'

export function IngredientGridCard() {
  const { userIngredients } = useIngredientStore()

  return (
    <>
      {userIngredients.length === 0 ? (
        <div className="text-center p-4 text-gray-500">
          No ingredients in your list. Press{' '}
          <Button size="icon" variant="outline" className="size-6">
            <Plus />
          </Button>{' '}
          to add ingredients.
        </div>
      ) : (
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-4">
          {userIngredients.map((ingredient) => (
            <div key={ingredient.idIngredient} className="max-w-sm w-full">
              <IngredientCard {...ingredient} />
            </div>
          ))}
        </div>
      )}
    </>
  )
}
