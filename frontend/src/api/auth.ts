// src/api/auth.ts

export async function login(username: string, password: string) {
  const res = await fetch("/api/login/auth", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
    body: JSON.stringify({ username, password }),
  })

  if (!res.ok) {
    throw new Error("Invalid credentials")
  }

  return res.text()
}
 
export async function logout() {
  const res = await fetch("/api/logout", {
    method: "POST",
    credentials: "include",
  })

  if (!res.ok) {
    throw new Error("Logout failed")
  }
}
