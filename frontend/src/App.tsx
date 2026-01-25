import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import { RequireAuth } from "./features/auth/RequireAuth.tsx";
import UserReviewsPage from "./pages/UserReviewsPage.tsx";
import EditReviewPage from "./pages/EditReviewPage.tsx";
import MainLayout from "./layouts/MainLayout.tsx";
import HomePage from "./pages/HomePage.tsx";
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />


        {/* routes with layout */}
        <Route element={<MainLayout />}>
          <Route path="/home" element={<HomePage />} />
          <Route path="/reviews/:username" element={<UserReviewsPage />} />
          <Route
            path="/reviews/:reviewId/edit"
            element={
              <RequireAuth>
                <EditReviewPage />
              </RequireAuth>
            }
          />

        
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
