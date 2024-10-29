import React, { useEffect, useState } from 'react';

const BookPage = () => {
    const [books, setBooks] = useState([]);
    const [page, setPage] = useState(0);
    const [pageSize, setPageSize] = useState(6);
    const [totalPages, setTotalPages] = useState(1);
    const [totalBooks, setTotalBooks] = useState(0);
    const [isbn, setIsbn] = useState('');

    useEffect(() => {
        fetchBooks();
    }, [page, pageSize]);

    const fetchBooks = async () => {
        const jwtToken = localStorage.getItem('jwtToken');
        if (!jwtToken) {
            console.error("JWT token not found in localStorage.");
            return;
        }

        const authHeader = `Bearer ${jwtToken}`;
        try {
            const response = await fetch(`http://localhost:8080/api/book/?page=${page}&size=${pageSize}`, {
                headers: {
                    'Authorization': authHeader,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) throw new Error(`Failed to fetch books: ${response.status}`);

            const data = await response.json();
            setBooks(data.elements.slice(0, pageSize) || []);
            setTotalPages(data.totalPages || 1);
            setTotalBooks(data.totalElements || 0);
        } catch (error) {
            console.error("An error occurred while fetching books:", error);
        }
    };

    const addBook = async () => {
        const jwtToken = localStorage.getItem('jwtToken');
        if (!jwtToken) {
            console.error("JWT token not found in localStorage.");
            return;
        }

        const authHeader = `Bearer ${jwtToken}`;
        try {
            const response = await fetch("http://localhost:8080/api/book/?isbn=" + isbn, {
                method: 'POST',
                headers: {
                    'Authorization': authHeader,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) throw new Error(`Failed to add book: ${response.status}`);

            alert("Book added successfully!");
            setIsbn('');
            fetchBooks();
        } catch (error) {
            console.error("An error occurred while adding the book:", error);
            alert("Failed to add the book.");
        }
    };

    const handlePageSizeChange = (e) => {
        setPageSize(Number(e.target.value));
        setPage(0);
    };

    const nextPage = () => {
        if (page < totalPages - 1) setPage(page + 1);
    };

    const previousPage = () => {
        if (page > 0) setPage(page - 1);
    };

    return (
        <div>
            <h1>Books</h1>
            <p>Total books in your collection: {totalBooks}</p>

            <div style={{ marginBottom: '20px' }}>
                <input
                    type="text"
                    placeholder="Enter ISBN"
                    value={isbn}
                    onChange={(e) => setIsbn(e.target.value)}
                    style={{ marginRight: '10px', padding: '5px' }}
                />
                <button onClick={addBook}>Add Book</button>
            </div>

            <div style={{ marginBottom: '20px' }}>
                <label htmlFor="pageSize">Books per page: </label>
                <select
                    id="pageSize"
                    value={pageSize}
                    onChange={handlePageSizeChange}
                    style={{ padding: '5px' }}
                >
                    <option value={3}>3</option>
                    <option value={6}>6</option>
                    <option value={10}>10</option>
                    <option value={20}>20</option>
                </select>
            </div>

            {books.length === 0 ? (
                <p>No books to display.</p>
            ) : (
                <div style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
                    gap: '20px'
                }}>
                    {books.map((book) => (
                        <div key={book.isbn} style={{ border: '1px solid #ccc', padding: '10px' }}>
                            <img src={book.coverUrl} alt={`${book.title} cover`} style={{ width: '150px', height: '200px', objectFit: 'cover' }} />
                            <h3>{book.title}</h3>
                            <p><strong>Author:</strong> {book.author}</p>
                            <p><strong>Pages:</strong> {book.numberOfPages}</p>
                        </div>
                    ))}
                </div>
            )}

            <div style={{ marginTop: '20px', display: 'flex', justifyContent: 'space-between' }}>
                <button onClick={previousPage} disabled={page === 0}>Previous</button>
                <span>Page {page + 1} of {totalPages}</span>
                <button onClick={nextPage} disabled={page === totalPages - 1}>Next</button>
            </div>
        </div>
    );
};

export default BookPage;
