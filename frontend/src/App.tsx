import { BrowserRouter, Routes, Route } from "react-router-dom"
import LoginPage from "./pages/LoginPage"
import { RequireAuth } from './auth/RequireAuth.tsx'
import UserReviewsPage from './pages/UserReviewsPage.tsx'
import EditReviewPage from "./pages/EditReviewPage.tsx"
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route
          path="/reviews/:username"
          element={
            <UserReviewsPage />
          }
        />
        
        <Route path="/reviews/:reviewId/edit"
          element={
          <RequireAuth>
            <EditReviewPage />
          </RequireAuth>
          }
        />


          
      </Routes>
    </BrowserRouter>
  )
}

export default App
