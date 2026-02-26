// display all books in a grid

import { useEffect, useState } from "react";
import type { Book } from "../types/book";
import { BookCard } from "../Components/BookCard";

export default function BooksPage() {
  const [books, setBooks] = useState<Book[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    fetch(`/api/books?page=${currentPage}&size=12&sort=title,asc`)
      .then((res) => res.json())
      .then((data) => {
        setBooks(data.content);
        setTotalPages(data.totalPages);
        setLoading(false);
      })
      .catch(() => setLoading(false));
  }, [currentPage]);

  if (loading) return <div className="loader">loading books...</div>;

  return (
    <div className="books-container">
      <h1>BOOKSSS</h1>
      
      <div className="book-grid">
        {books.map((book) => (
          <BookCard key={book.isbn} book={book} />
        ))}
      </div>

      {/* pagination */}
      <div className="pagination">
        <button 
          disabled={currentPage === 0} 
          onClick={() => setCurrentPage(p => p - 1)}
        >
          Previous
        </button>
        <span>Page {currentPage + 1} of {totalPages}</span>
        <button 
          disabled={currentPage >= totalPages - 1} 
          onClick={() => setCurrentPage(p => p + 1)}
        >
          Next
        </button>
      </div>
    </div>
  );
}