// component for displaying all reviews for a given book
import { useEffect, useState } from "react";
import type { Review } from "../../types/review";
import { ReviewCard } from "../../Components/ReviewCard";
interface Props {
  isbn: string;
}

export function BookReviewsList({ isbn }: Props) {
  const [reviews, setReviews] = useState<Review[]>([]);
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    async function fetchReviews() {
      try {
        setLoading(true);
        // match backend: /api/reviews/books/{isbn}
        const res = await fetch(`/api/reviews/books/${isbn}?page=${currentPage}&size=5`);
        const data = await res.json();
        
        setReviews(data.content);
        setTotalPages(data.totalPages);
      } catch (error) {
        console.error("Error fetching reviews:", error);
      } finally {
        setLoading(false);
      }
    }

    if (isbn) fetchReviews();
  }, [isbn, currentPage]);

  if (loading && reviews.length === 0) return <p>Loading reviews...</p>;

  return (
    <div className="book-reviews-list">
      <h3>Community Reviews</h3>
      
      {reviews.length === 0 ? (
        <p className="no-reviews">No reviews left, be the first</p>
      ) : (
        <div className="review-stack">
          {reviews.map((review) => (
            <ReviewCard 
              key={review.id} 
              review={review} 
              showReviewer={true}
            />
          ))}
        </div>
      )}

      {totalPages > 1 && (
        <div className="pagination-small">
          <button 
            disabled={currentPage === 0} 
            onClick={() => setCurrentPage(p => p - 1)}
          >
            Prev
          </button>
          <span>{currentPage + 1} / {totalPages}</span>
          <button 
            disabled={currentPage >= totalPages - 1} 
            onClick={() => setCurrentPage(p => p + 1)}
          >
            Next
          </button>
        </div>
      )}
    </div>
  );
}