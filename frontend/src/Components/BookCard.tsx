import type { Book } from "../types/book";
import { Link } from "react-router-dom";

export function BookCard({ book }: { book: Book }) {
  return (
    <div className="book-card">
      <img 
        src={book.imageUrl || "../placeholder-book.jpg"} 
        alt={book.title} 
        className="book-cover"
      />
      <div className="book-info">
        <h3 className="book-title">
          <Link to={`/books/${book.isbn}`}>{book.title}</Link>
        </h3>
        <p className="book-author">by {book.authorName}</p>
        <span className="isbn-tag">ISBN: {book.isbn}</span>
      </div>
    </div>
  );
}