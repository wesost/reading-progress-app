import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useAuth } from "../features/auth/useAuth";
import { useNavigate } from "react-router-dom";
import { ReviewCard } from "../Components/ReviewCard";
type Review = {
  id: number;
  reviewerUsername: string;
  bookIsbn: string;
  bookTitle: string;
  rating: number;
  reviewText: string;
  createdAt: string;
  dateFinished: string | null;
};

export default function UserReviewsPage() {
  const { username } = useParams();
  const { user, isAuthenticated } = useAuth();

  const [reviews, setReviews] = useState<Review[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const navigate = useNavigate();

  const isOwner = isAuthenticated && user?.username === username;
  const isAdmin = user?.role === "ROLE_ADMIN";

  useEffect(() => {
    fetch(`/api/reviews/${username}/reviews?page=${currentPage}&size=10`)
      .then((res) => res.json())
      .then((data) => {
        setReviews(data.content);
        setTotalPages(data.totalPages);
      });
  }, [username, currentPage]);

  async function handleDelete(reviewId: number) {
    await fetch(`/api/reviews/${reviewId}`, {
      method: "DELETE",
      credentials: "include",
    });

    setReviews(reviews.filter((r) => r.id !== reviewId));
  }

  return (
    <div>
      <h1>{username}'s Reviews</h1>

      <div className="review-grid">
        {reviews.map(review => (
          <ReviewCard key={review.id}
          review={review}
          showReviewer={false}
          canManage={isOwner || isAdmin}
          onDelete={(id) => handleDelete(id)}
          onEdit={(id) => navigate(`/reviews/${id}'/edit`)}
        />
        ))}
      </div>

      {totalPages > 1 && (
        <div className="pagination">
          <button 
            disabled={currentPage === 0} 
            onClick={() => setCurrentPage(prev => prev - 1)}
          >
            Previous
          </button>
          
          <span>Page {currentPage + 1} of {totalPages}</span>

          <button 
            disabled={currentPage >= totalPages - 1} 
            onClick={() => setCurrentPage(prev => prev + 1)}
          >
            Next
          </button>
        </div>
      )}
    </div>
  );
}
