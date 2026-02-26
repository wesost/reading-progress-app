import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import { RequireAuth } from "./features/auth/RequireAuth.tsx";
import UserReviewsPage from "./pages/UserReviewsPage.tsx";
import EditReviewPage from "./pages/EditReviewPage.tsx";
import MainLayout from "./layouts/MainLayout.tsx";
import HomePage from "./pages/HomePage.tsx";
import SignupPage from "./pages/SignupPage.tsx";
// import LeaveReviewPage from "./pages/LeaveReviewPage.tsx";
import BooksPage from "./pages/BooksPage.tsx";
import BookPage from "./pages/BookPage.tsx";
import ReviewsPage from "./pages/BookReviewsPage.tsx";
import { AdminRoute } from "./features/admin/AdminRoute.tsx";
import AdminDashboard from "./pages/AdminDashboard.tsx";
import UsersPage from "./pages/UsersPage.tsx";
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />


        {/* routes with layout */}
        <Route element={<MainLayout />}>
          <Route path="/home" element={<HomePage />} />
          <Route path="/reviews/:username" element={<UserReviewsPage />} />
          <Route path="/books" element={<BooksPage />} />
          <Route path="/books/:isbn" element={<BookPage />} />
          <Route path="/reviews" element={<ReviewsPage />} />
          <Route
            path="/reviews/:reviewId/edit"
            element={
              <RequireAuth>
                <EditReviewPage />
              </RequireAuth>
            }
          />
          {/* admin routes */}
          <Route path="/admin" element={
            <AdminRoute>
              <AdminDashboard />
            </AdminRoute>
          }
          />
          <Route path="/users" element={
            <AdminRoute>
              <UsersPage/>
            </AdminRoute>
          }
          />


          {/* <Route
            path="/reviews/books/:isbn"
            element={
              <RequireAuth>
                <LeaveReviewPage />
              </RequireAuth>
            }
          /> */}

        
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
