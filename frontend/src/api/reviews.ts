export async function getReview(id: number) {
  const res = await fetch(`/api/reviews/${id}`, {
    credentials: "include",
  })

  if (!res.ok) throw new Error("Failed to load review")
  return res.json()
}

export async function updateReview(
  id: number,
  reviewText: string,
  rating: number
) {
  const res = await fetch(`/api/reviews/${id}`, {
    method: "PATCH",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ reviewText, rating }),
  })

  if (!res.ok) throw new Error("Update failed")
}
