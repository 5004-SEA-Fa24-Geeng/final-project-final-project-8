'use client'

export const Header = () => {
  return (
    <header className="bg-white shadow-lg sticky top-0 z-50 mx-auto px-6 sm:px-8 flex justify-between h-16 items-center">
      <div className="flex-shrink-0 flex items-center">
        <a href="/" className="text-xl font-bold flex items-center">
          <h1 className="text-3xl">🧙‍♂️</h1>
          FoodWizard
        </a>
      </div>
    </header>
  )
}
