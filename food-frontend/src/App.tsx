'use client'
import { Routes, Route } from 'react-router-dom'
import { RecipeDetailView } from '@/components/custom/recipeDetailView.tsx'
import HomePage from '@/pages/homePage'
import { Header } from '@/components/custom/header'

function App() {
  return (
    <div>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/recipe/:id" element={<RecipeDetailView />} />
      </Routes>
    </div>
  )
}

export default App
