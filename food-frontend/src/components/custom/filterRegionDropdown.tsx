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
import { initializeRegions } from '@/store/ingredientStore'

export function FilterRegionDropdown() {
  const [selectedRegion, setSelectedRegion] = useState('All regions')
  const [isOpen, setIsOpen] = useState(false)
  const { setFilterRegion, availableRegions } = useIngredientStore()

  useEffect(() => {
    console.log('Loading regions...')
    initializeRegions()
  }, [])

  return (
    <DropdownMenu open={isOpen} onOpenChange={setIsOpen}>
      <DropdownMenuTrigger>
        <Button variant="outline" className="flex justify-between gap-1 w-40">
          {selectedRegion}
          <ChevronDown
            className={`h-4 w-4 shrink-0 opacity-50 transition-transform duration-200 ${
              isOpen ? 'rotate-180' : 'rotate-0'
            }`}
          />
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent className="w-40">
        <DropdownMenuLabel>Filter by region</DropdownMenuLabel>
        <DropdownMenuSeparator />
        <DropdownMenuRadioGroup
          value={selectedRegion}
          onValueChange={setSelectedRegion}
        >
          <DropdownMenuRadioItem
            value="All regions"
            onClick={() => setFilterRegion(null)}
          >
            All regions
          </DropdownMenuRadioItem>
          {availableRegions.map((area) => (
            <DropdownMenuRadioItem
              value={area}
              key={area}
              onClick={() => setFilterRegion(area)}
            >
              {area}
            </DropdownMenuRadioItem>
          ))}
        </DropdownMenuRadioGroup>
      </DropdownMenuContent>
    </DropdownMenu>
  )
}
