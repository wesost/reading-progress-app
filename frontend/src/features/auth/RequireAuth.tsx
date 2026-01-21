import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "./useAuth";
import type { JSX } from "react";

export function RequireAuth({ children }: { children: JSX.Element }) {
  const { isAuthenticated, loading } = useAuth();
  const location = useLocation();

  if (loading) {
    return null
  }
  if (!isAuthenticated) {
    return <Navigate to="/login" replace state={{ from: location }} />;
  }

  return children;
}
