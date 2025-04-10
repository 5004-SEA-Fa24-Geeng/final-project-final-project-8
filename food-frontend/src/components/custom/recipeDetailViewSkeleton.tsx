import { Skeleton } from '../ui/skeleton'
import { Button } from '../ui/button'
import { ArrowLeft } from 'lucide-react'

export function RecipeSkeleton() {
  return (
    <div className="container mx-auto p-4">
      {/* Back button */}
      <div className="mb-4">
        <Button variant="outline" size="sm" disabled>
          <ArrowLeft className="mr-2 h-4 w-4" /> Back to Recipes
        </Button>
      </div>

      <div className="bg-white rounded-lg shadow-md overflow-hidden">
        {/* Image skeleton */}
        <Skeleton className="w-full h-64" />

        <div className="p-6">
          <div className="flex flex-col gap-3 mb-3">
            {/* Title and download button */}
            <div className="flex justify-between">
              <Skeleton className="h-10 w-3/4" />
              <Skeleton className="h-10 w-10 rounded-md" />
            </div>

            {/* Categories */}
            <div className="flex gap-4">
              <Skeleton className="h-6 w-24 rounded-full" />
              <Skeleton className="h-6 w-24 rounded-full" />
            </div>
          </div>

          {/* Ingredients section */}
          <div className="my-3">
            <Skeleton className="h-7 w-32 mb-3" />
            <div className="grid grid-cols-1 md:grid-cols-2 gap-2">
              {[...Array(8)].map((_, index) => (
                <Skeleton key={index} className="h-6" />
              ))}
            </div>
          </div>

          {/* Instructions section */}
          <div className="mt-6 my-3">
            <Skeleton className="h-7 w-32 mb-3" />
            <div className="space-y-2">
              {[...Array(6)].map((_, index) => (
                <Skeleton key={index} className="h-4 w-full" />
              ))}
            </div>
          </div>

          {/* Video tutorial section */}
          <div className="mt-6 my-3">
            <Skeleton className="h-7 w-32 mb-3" />
            <Skeleton className="w-full h-0 pt-[56.25%]" />
          </div>
        </div>
      </div>
    </div>
  )
}
