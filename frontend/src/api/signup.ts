import type { UserDto } from "../types/user";

/**
 * Sends the signup data to the Spring Boot backend.
 * Path: POST /api/users
 */
export async function signup(userData: UserDto): Promise<void> {
  const response = await fetch("/api/users", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(userData),
  });

  if (!response.ok) {
    // Attempt to parse backend error message (e.g., "Username already taken")
    const errorData = await response.json().catch(() => ({}));
    throw new Error(errorData.message || "An error occurred during signup.");
  }

  // We don't necessarily need the returned UserResponseDto here 
  // because the user isn't logged in yet—they need to verify first.
  return;
}