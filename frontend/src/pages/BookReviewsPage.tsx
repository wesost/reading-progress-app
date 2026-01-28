// display all reviews
import { useEffect, useState } from "react";
import type { Review } from "../types/review";
import { ReviewCard } from "../Components/ReviewCard";
import { useAuth } from "../features/auth/useAuth";
import { useNavigate } from "react-router-dom";

export default function ReviewsPage() {
  const [reviews, setReviews] = useState<Review[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);
  
  const { user } = useAuth();
  const navigate = useNavigate();
  const isAdmin = user?.role === "ROLE_ADMIN";

  const fetchAllReviews = async () => {
    setLoading(true);
    try {
      const res = await fetch(`/api/reviews?page=${currentPage}&size=10&sort=createdAt,desc`);
      const data = await res.json();
      setReviews(data.content);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error("Failed to fetch reviews", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAllReviews();
  }, [currentPage]);

  const handleDelete = async (reviewId: number) => {
    if (!window.confirm("Are you sure you want to delete this review?")) return;

    const res = await fetch(`/api/reviews/${reviewId}`, {
      method: "DELETE",
      credentials: "include",
    });

    if (res.ok) {
      setReviews(prev => prev.filter(r => r.id !== reviewId));
    }
  };

  if (loading && reviews.length === 0) return <div>Loading reviews...</div>;

  return (
    <div className="reviews-page-container">
      <h1>All reviews</h1>
      
      <div className="review-stack">
        {reviews.map((review) => (
          <ReviewCard 
            key={review.id} 
            review={review} 
            showReviewer={true}
            canManage={isAdmin || (user?.username === review.reviewerUsername)}
            onDelete={handleDelete}
            onEdit={(id) => navigate(`/reviews/${id}/edit`)}
          />
        ))}
      </div>

      {totalPages > 1 && (
        <div className="pagination">
          <button disabled={currentPage === 0} onClick={() => setCurrentPage(p => p - 1)}>
            Previous
          </button>
          <span>Page {currentPage + 1} of {totalPages}</span>
          <button disabled={currentPage >= totalPages - 1} onClick={() => setCurrentPage(p => p + 1)}>
            Next
          </button>
        </div>
      )}
    </div>
  );
}