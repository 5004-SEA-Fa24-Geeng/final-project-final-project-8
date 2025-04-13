'use client'

import { ChevronDown } from 'lucide-react'
import { Button } from '@/components/ui/button'
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuRadioGroup,
  DropdownMenuRadioItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu'
import { useEffect, useState } from 'react'
import { useIngredientStore } from '@/store/ingredientStore'
import { initializeCategories } from '@/store/ingredientStore'

export function FilterCategoryDropdown() {
  const [selectedCategory, setSelectedCategory] = useState('All Categories')
  const [isOpen, setIsOpen] = useState(false)
  const { setFilterCategory, availableCategories } = useIngredientStore()

  useEffect(() => {
    initializeCategories()
  }, [])

  return (
    <DropdownMenu open={isOpen} onOpenChange={setIsOpen}>
      <DropdownMenuTrigger>
        <Button
          variant="outline"
          className="flex justify-between gap-1 w-38 sm:w-40"
        >
          {selectedCategory}
          <ChevronDown
            className={`h-4 w-4 shrink-0 opacity-50 transition-transform duration-200 ${
              isOpen ? 'rotate-180' : 'rotate-0'
            }`}
          />
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent className="w-38 sm:w-40">
        <DropdownMenuLabel>Filter by Category</DropdownMenuLabel>
        <DropdownMenuSeparator />
        <DropdownMenuRadioGroup
          value={selectedCategory}
          onValueChange={setSelectedCategory}
        >
          <DropdownMenuRadioItem
            value="All Categories"
            onClick={() => setFilterCategory(null)}
          >
            All Categorys
          </DropdownMenuRadioItem>
          {availableCategories.map((Category) => (
            <DropdownMenuRadioItem
              value={Category}
              key={Category}
              onClick={() => setFilterCategory(Category)}
            >
              {Category}
            </DropdownMenuRadioItem>
          ))}
        </DropdownMenuRadioGroup>
      </DropdownMenuContent>
    </DropdownMenu>
  )
}
