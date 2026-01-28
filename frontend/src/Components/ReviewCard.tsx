import type { Review } from "../types/review";
import { Link } from "react-router-dom";

interface ReviewCardProps {
  review: Review;
  showReviewer?: boolean; // true on homepage false on user profile
  onDelete?: (id: number) => void;
  onEdit?: (id: number) => void;
  canManage?: boolean; // show edit/delete
}

export function ReviewCard({ 
  review, 
  showReviewer = true, 
  onDelete, 
  onEdit, 
  canManage = false 
}: ReviewCardProps) {
  
  // Format date
  const dateDisplay = review.dateFinished 
    ? new Date(review.dateFinished).toLocaleDateString() 
    : "Recently";

  return (
    <div className="review-card">
      <div className="review-header">
        <h3 className="book-title">{review.bookTitle}</h3>
        <span className="rating-badge">{review.rating} / 10</span>
      </div>

      {showReviewer && (
        <p className="reviewer-name">
          by <Link to={`/reviews/${review.reviewerUsername}`}>{review.reviewerUsername}</Link>
        </p>
      )}

      <p className="review-text">"{review.reviewText}"</p>

      <div className="review-footer">
        <span className="date-finished">Finished: {dateDisplay}</span>
        
        {canManage && (
          <div className="action-buttons">
            <button 
              className="btn-edit" 
              onClick={() => onEdit?.(review.id)}
            >
              Edit
            </button>
            <button 
              className="btn-delete" 
              onClick={() => onDelete?.(review.id)}
            >
              Delete
            </button>
          </div>
        )}
      </div>
    </div>
  );
}