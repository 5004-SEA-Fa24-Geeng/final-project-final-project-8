'use client'

import { Skeleton } from '@/components/ui/skeleton'

export function RecipePreviewCardSkeleton() {
  return (
    <div className="w-full rounded-lg shadow-sm border border-gray-200 overflow-hidden">
      {/* Image skeleton */}
      <Skeleton className="w-full aspect-square" />

      {/* Content skeleton */}
      <div className="flex justify-between p-1 m-1">
        {/* Title skeleton */}
        <Skeleton className="py-1 w-3/4 h-6" />

        {/* Button skeleton */}
        <Skeleton className="w-8 h-8 rounded" />
      </div>
    </div>
  )
}
