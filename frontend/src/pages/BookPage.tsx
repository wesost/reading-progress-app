// single book view

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import type { Book } from "../types/book";
import { BookReviewsList } from "../features/reviews/BookReviewsList";

export default function BookPage() {
  const { isbn } = useParams<{ isbn: string }>();
  const [book, setBook] = useState<Book | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchBook() {
      try {
        setLoading(true);
        const res = await fetch(`/api/books/${isbn}`);
        if (!res.ok) throw new Error("Book not found");
        
        const data = await res.json();
        setBook(data);
      } catch (err: any) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }

    if (isbn) fetchBook();
  }, [isbn]);

  if (loading) return <div className="loader">Loading book details...</div>;
  if (error) return <div className="error-container">{error}</div>;
  if (!book) return null;

  return (
    <div className="book-detail-container">
      <div className="book-detail-layout">
        <div className="book-image-section">
          <img 
            src={book.imageUrl || "../placeholder-book.jpg"} 
            alt={book.title} 
            className="book-detail-cover"
          />
        </div>

        <div className="book-info-section">
          <h1>{book.title}</h1>
          <h2 className="author-name">by {book.authorName}</h2>
          <div className="book-meta">
            <p><strong>ISBN-13:</strong> {book.isbn}</p>
          </div>
          
          <div className="book-actions">
            {/* add later */}
            <button className="btn-secondary">review this book</button>
          </div>
        </div>
      </div>

      <hr />

      <section className="book-reviews-section">
        <BookReviewsList isbn={book.isbn} />
      </section>
    </div>
  );
}