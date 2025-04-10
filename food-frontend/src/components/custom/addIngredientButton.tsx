import { Button } from '../ui/button'
import { Plus } from 'lucide-react'
import { Popover, PopoverContent, PopoverTrigger } from '../ui/popover'
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from '../ui/command'
import { useIngredientStore } from '../../store/ingredientStore'
import { useEffect, useState } from 'react'
import { Ingredient } from '@/models/ingredientModel'
import { initializeIngredients } from '@/store/ingredientStore'

export function AddIngredientButton() {
  useEffect(() => {
    console.log('Initializing ingredients...')
    initializeIngredients()
  }, [])

  const INGREDIENT_COUNT = 50
  const [open, setOpen] = useState(false)
  const [searchQuery, setSearchQuery] = useState('')

  const availableIngredients = useIngredientStore(
    (state) => state.availableIngredients
  )

  const filteredIngredients = searchQuery
    ? availableIngredients.filter((ingredient) =>
        ingredient.nameIngredient
          .toLowerCase()
          .includes(searchQuery.toLowerCase())
      )
    : (() => {
        const shuffled = [...availableIngredients]
        let size = shuffled.length
        const min = Math.max(0, size - INGREDIENT_COUNT)
        while (size > min) {
          const randomIndex = Math.floor(Math.random() * (size + 1))
          const temp = shuffled[randomIndex]
          shuffled[randomIndex] = shuffled[size]
          shuffled[size] = temp
          size--
        }

        // Take first 50 and sort alphabetically
        return shuffled
          .slice(min)
          .sort((a, b) => a.nameIngredient.localeCompare(b.nameIngredient))
      })()

  const handleSelectIngredient = (ingredient: Ingredient) => {
    useIngredientStore.getState().addUserIngredient(ingredient)
    setOpen(false) // Close the popover after selection
    setSearchQuery('') // Clear the search query
  }

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger>
        <Button size="icon" variant="outline" className="size-6">
          <Plus />
        </Button>
      </PopoverTrigger>
      <PopoverContent>
        <Command>
          <CommandInput
            placeholder="Search ingredients"
            value={searchQuery}
            onValueChange={setSearchQuery}
          />
          <CommandList className="max-h-[300px] overflow-auto">
            <CommandEmpty>No ingredients found.</CommandEmpty>
            <CommandGroup heading="Ingredients">
              {filteredIngredients.map((ingredient) => (
                <CommandItem
                  key={ingredient.idIngredient}
                  onSelect={() => handleSelectIngredient(ingredient)}
                >
                  {ingredient.nameIngredient}
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  )
}
