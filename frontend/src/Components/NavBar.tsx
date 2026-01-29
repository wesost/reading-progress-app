import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../features/auth/useAuth";

export default function Navbar() {
  const { user, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout();
    navigate("/login"); // Redirect to login after signing out
  };

  return (
    <nav className="navbar">
      <div className="nav-logo">
        <Link to="/">BookArchive</Link>
      </div>

      <ul className="nav-links">
        {/* --- COMMON LINKS (Visible to Everyone) --- */}
        <li><Link to="/home">Home</Link></li>
        <li><Link to="/books/search">Search</Link></li>

        {/* --- LOGGED OUT ONLY --- */}
        {!isAuthenticated && (
          <>
            <li><Link to="/login">Login</Link></li>
            <li><Link to="/signup">Sign Up</Link></li>
          </>
        )}

        {/* --- LOGGED IN (USER OR ADMIN) --- */}
        {isAuthenticated && (
          <>
            <li><Link to={`/reviews/${user?.username}`}>My Reviews</Link></li>
            <li><Link to="/reviews/new">Leave Review</Link></li>
            
            {/* --- ADMIN ONLY --- */}
            {user?.role === "ROLE_ADMIN" && (
              <li><Link to="/admin" className="admin-link">Admin Dashboard</Link></li>
            )}

            <li>
              <button onClick={handleLogout} className="logout-btn">
                Logout ({user?.username})
              </button>
            </li>
          </>
        )}
      </ul>
    </nav>
  );
}