import { Navigate } from "react-router-dom";
import { useAuth } from "../auth/useAuth";
import type { JSX } from "react";

interface AdminRouteProps {
  children: JSX.Element;
}

/**
 * A specialized route guard that checks if the user is authenticated
 * and has the 'ROLE_ADMIN' authority.
 */
export function AdminRoute({ children }: AdminRouteProps) {
  const { user, isAuthenticated, loading } = useAuth();

  // wait for auth check to complete to avoid flickering or false redirects
  if (loading) {
    return (
      <div className="loading-container">
        <p>Verifying credentials...</p>
      </div>
    );
  }

  // if not logged in OR logged in but not an admin, boot them to Home
  if (!isAuthenticated || user?.role !== "ROLE_ADMIN") {
    console.warn("Unauthorized access attempt to Admin area.");
    return <Navigate to="/home" replace />;
  }

  // render the protected content if authorized
  return children;
}