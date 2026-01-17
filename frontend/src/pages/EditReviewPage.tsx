import { useEffect, useState } from "react"
import { useParams, useNavigate } from "react-router-dom"
import { getReview, updateReview } from "../api/reviews"

export default function EditReviewPage() {
  const { reviewId } = useParams()
  const navigate = useNavigate()

  const [reviewText, setReviewText] = useState("")
  const [rating, setRating] = useState<number>(0)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    async function loadReview() {
      try {
        const review = await getReview(Number(reviewId))
        setReviewText(review.reviewText)
        setRating(review.rating)
      } catch {
        setError("You are not allowed to edit this review.")
      } finally {
        setLoading(false)
      }
    }

    loadReview()
  }, [reviewId])

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()

    try {
      await updateReview(Number(reviewId), reviewText, rating)
      navigate(-1) // go back
    } catch {
      setError("Update failed.")
    }
  }

  if (loading) return <p>Loading...</p>
  if (error) return <p style={{ color: "red" }}>{error}</p>

  return (
    <form onSubmit={handleSubmit}>
      <h1>Edit Review</h1>

      <textarea
        value={reviewText}
        onChange={e => setReviewText(e.target.value)}
        required
      />

      <input
        type="number"
        min={0}
        max={10.0}
        value={rating}
        onChange={e => setRating(Number(e.target.value))}
        required
      />

      <button type="submit">Save</button>
    </form>
  )
}
