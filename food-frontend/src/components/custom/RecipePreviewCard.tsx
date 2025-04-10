'use client'

import { RecipePreview } from '@/models/recipePreviewModel'
import { Info } from 'lucide-react'
import { Button } from '../ui/button'
import { Link } from 'react-router-dom'

export function RecipePreviewCard(recipePreview: RecipePreview) {
  return (
    <div className="w-full rounded-lg shadow-sm border border-gray-200 overflow-hidden">
      <img
        src={recipePreview.mealImg}
        alt={recipePreview.mealName}
        className="w-full aspect-square"
      ></img>
      <div className="flex justify-between p-1 m-1">
        <h1
          className="py-1 font-bold text-lg md:text-xl truncate"
          title={recipePreview.mealName}
        >
          {recipePreview.mealName}
        </h1>
        <Link to={`/recipe/${recipePreview.idMeal}`}>
          <Button variant="outline" size="icon">
            <Info />
          </Button>
        </Link>
      </div>
    </div>
  )
}
