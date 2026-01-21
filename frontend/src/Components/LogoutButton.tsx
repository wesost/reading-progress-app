import { useAuth } from "../features/auth/useAuth";

export function LogoutButton() {
  const { logout } = useAuth();

  return <button onClick={logout}>Logout</button>;
}
