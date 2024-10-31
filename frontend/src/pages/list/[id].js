// pages/list/[id].js
import { useRouter } from 'next/router';
import { useEffect, useState } from 'react';
import axiosInstance from '../../utils/axiosInstance';

export default function ListBooks() {
    const router = useRouter();
    const { id, name } = router.query; // Get the list ID and name from query parameters
    const [books, setBooks] = useState([]);
    const [totalBooks, setTotalBooks] = useState(0); // State for the number of books
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const pageSize = 6;

    useEffect(() => {
        if (id) {
            fetchBooksInList();
        }
    }, [id, page]);

    const fetchBooksInList = async () => {
        try {
            const response = await axiosInstance.get(`/reading-list/${id}/books?page=${page}&size=${pageSize}`);
            const data = response.data;
            setBooks(data.elements.slice(0, pageSize) || []);
            setTotalPages(data.totalPages || 1);
            setTotalBooks(data.totalElements || 0); // Set the total number of books
        } catch (error) {
            console.error("Error fetching books in list:", error);
        }
    };

    const nextPage = () => {
        if (page < totalPages - 1) setPage(page + 1);
    };

    const previousPage = () => {
        if (page > 0) setPage(page - 1);
    };

    return (
        <div>
            <h1>Books in Reading List: {name}</h1> {/* Display the list name from query */}
            <p>Total books: {totalBooks}</p>

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
}
