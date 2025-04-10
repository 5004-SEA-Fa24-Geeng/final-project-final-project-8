'use client'

import { useState, useEffect } from 'react'
import { useParams, Link } from 'react-router-dom'
import { RecipeDetail } from '@/models/recipeDetailModel'
import { Button } from '../ui/button'
import { ArrowLeft, Download } from 'lucide-react'
import ingredientService from '@/services/ingredientService'
import { RecipeSkeleton } from './recipeDetailViewSkeleton'

// Function to download recipe as JSON
const downloadRecipeAsJson = (recipe: RecipeDetail) => {
  // Create a Blob containing the recipe data in JSON format
  const recipeJson = JSON.stringify(recipe, null, 2)
  const blob = new Blob([recipeJson], {
    type: 'application/json',
  })

  // Create a URL for the Blob
  const url = URL.createObjectURL(blob)

  // Create an anchor element and set attributes for download
  const anchor = document.createElement('a')
  anchor.href = url
  anchor.download = `${recipe.recipeName
    .replace(/\s+/g, '-')
    .toLowerCase()}-recipe.json`

  // Append to body, click, and remove
  document.body.appendChild(anchor)
  anchor.click()

  // Clean up
  document.body.removeChild(anchor)
  URL.revokeObjectURL(url)
}

// Function to convert YouTube URL to embed format
const getYouTubeEmbedUrl = (url: string): string => {
  try {
    // Handle different YouTube URL formats
    const regExp =
      /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|&v=)([^#&?]*).*/
    const match = url.match(regExp)

    // If match found, return the embed URL with the video ID
    return match && match[2].length === 11
      ? `https://www.youtube.com/embed/${match[2]}`
      : url // Return original if no match
  } catch (error) {
    console.error('Error parsing YouTube URL:', error)
    return url // Return original URL in case of error
  }
}

// YouTube embed component
interface YouTubeEmbedProps {
  url: string
}

function YouTubeEmbed({ url }: YouTubeEmbedProps) {
  const embedUrl = getYouTubeEmbedUrl(url)

  return (
    <div className="relative pt-[56.25%]">
      <iframe
        className="absolute top-0 left-0 w-full h-full"
        src={embedUrl}
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
        allowFullScreen
        title="Recipe video tutorial"
      />
    </div>
  )
}

export function RecipeDetailView() {
  const { id } = useParams<{ id: string }>()
  const [recipe, setRecipe] = useState<RecipeDetail | null>(null)
  const [loading, setLoading] = useState<boolean>(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    window.scrollTo(0, 0)
  }, [])

  useEffect(() => {
    const fetchRecipe = async (id: string) => {
      try {
        setLoading(true)
        const fetched_recipe = await ingredientService.getRecipe(id)
        setRecipe(fetched_recipe)
        setLoading(false)
      } catch (error) {
        setError('An error occurred while fetching the recipe')
        setLoading(false)
        console.error(error)
      }
    }
    if (id) {
      fetchRecipe(id)
    }
  }, [id])

  if (loading) {
    return <RecipeSkeleton />
  }

  if (error || !recipe) {
    return (
      <div className="flex flex-col">
        <div className="p-4">
          <Link to="/">
            <Button variant="outline" size="sm" className="mb-4">
              <ArrowLeft className="mr-2 h-4 w-4" /> Back to Recipes
            </Button>
          </Link>
        </div>
        <div className="flex-1 flex items-center justify-center">
          <div className="text-center p-8 text-2xl sm:text-4xl font-medium text-gray-500">
            {error || '🪄 Recipe not found'}
          </div>
        </div>
      </div>
    )
  }

  return (
    <div className="container mx-auto p-4">
      <Link to="/">
        <Button variant="outline" size="sm" className="mb-4">
          <ArrowLeft className="mr-2 h-4 w-4" /> Back to Recipes
        </Button>
      </Link>

      <div className="bg-white rounded-lg shadow-md overflow-hidden">
        <img
          src={recipe.image}
          alt={recipe.recipeName}
          className="w-full h-64 object-cover"
        />
        <div className="p-6">
          <div className="flex flex-col gap-3 mb-3">
            <div className="flex gap-3">
              <h1 className="text-3xl font-bold">{recipe.recipeName}</h1>
              <Button
                size="icon"
                variant="outline"
                onClick={() => downloadRecipeAsJson(recipe)}
              >
                <Download />
              </Button>
            </div>

            <div className="flex gap-4">
              <div className="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-sm">
                {recipe.category}
              </div>
              <div className="bg-green-100 text-green-800 px-3 py-1 rounded-full text-sm">
                {recipe.area}
              </div>
            </div>
          </div>

          <h2 className="text-xl font-semibold my-3">Ingredients</h2>
          <ul className="grid grid-cols-1 md:grid-cols-2 gap-2 my-3">
            {recipe.ingredients.map((ingredient, index) => (
              <li key={index} className="flex items-center gap-1">
                <span className="font-semibold">{recipe.measures[index]}</span>
                <span>{ingredient}</span>
              </li>
            ))}
          </ul>

          <h2 className="text-xl font-semibold mt-6 my-3">Instructions</h2>
          <p className="whitespace-pre-line my-3">{recipe.instructions}</p>

          {recipe.youtube && (
            <>
              <h2 className="text-xl font-semibold my-3">Video Tutorial</h2>
              <YouTubeEmbed url={recipe.youtube} />
            </>
          )}
        </div>
      </div>
    </div>
  )
}
