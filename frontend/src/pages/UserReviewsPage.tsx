import { useParams } from "react-router-dom"
import { useEffect, useState } from "react"
import { useAuth } from "../auth/useAuth"
import { useNavigate } from "react-router-dom"
type Review = {
  id: number
  reviewerUsername: string
  bookIsbn: string
  bookTitle: string
  rating: number
  reviewText: string
  createdAt: string
  dateFinished: string | null
}


export default function UserReviewsPage() {
  const { username } = useParams()
  const { user, isAuthenticated } = useAuth()

  const [reviews, setReviews] = useState<Review[]>([])
  const navigate = useNavigate()

  const isOwner = isAuthenticated && user?.username === username
  const isAdmin = user?.role === "ROLE_ADMIN"

  useEffect(() => {
    fetch(`/api/reviews/${username}/reviews`)
      .then(res => res.json())
      .then(data => {
        setReviews(data.content)
      })
  }, [username])

  async function handleDelete(reviewId: number) {
    await fetch(`/api/reviews/${reviewId}`, {
      method: "DELETE",
      credentials: "include"
    })

    setReviews(reviews.filter(r => r.id !== reviewId))
  }

  return (
    <div>
      <h1>User Reviews</h1>

      {reviews.length === 0 ? (
        <p>User {username} has no reviews yet.</p>
      ) : (
        reviews.map(review => (
          <div key={review.id}>
            <h3>{review.bookTitle}</h3>
            <p>{review.reviewText}</p>
            <p>{review.rating}</p>
             {(isOwner || isAdmin) && (
              <>
              
                <button onClick={() => handleDelete(review.id)}>
                    Delete
                </button>

                <button onClick={() => navigate(`/reviews/${review.id}/edit`)}>
                    Edit
                </button>
              </>
      )}
          </div>
        ))
      )}
    </div>
  )
}
