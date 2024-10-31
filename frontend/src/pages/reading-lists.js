import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import axiosInstance from '../utils/axiosInstance';

export default function ReadingLists() {
    const router = useRouter();
    const [readingLists, setReadingLists] = useState([]);
    const [page, setPage] = useState(0);
    const [pageSize, setPageSize] = useState(6);
    const [totalPages, setTotalPages] = useState(1);
    const [totalLists, setTotalLists] = useState(0);
    const [newListName, setNewListName] = useState('');
    const [showModal, setShowModal] = useState(false);
    const [selectedListId, setSelectedListId] = useState(null);
    const [isbn, setIsbn] = useState('');

    useEffect(() => {
        fetchReadingLists();
    }, [page, pageSize]);

    const fetchReadingLists = async () => {
        try {
            const response = await axiosInstance.get(`/reading-list/?page=${page}&size=${pageSize}`);
            const data = response.data;
            setReadingLists(data.elements.slice(0, pageSize) || []);
            setTotalPages(data.totalPages || 1);
            setTotalLists(data.totalElements || 0);
        } catch (error) {
            console.error("Error fetching reading lists:", error);
        }
    };

    const createReadingList = async () => {
        if (!newListName.trim()) {
            alert("Please enter a name for the reading list.");
            return;
        }

        try {
            await axiosInstance.post(`/reading-list/`, {
                name: newListName
            });

            alert("Reading list created successfully!");
            setNewListName('');
            fetchReadingLists();
        } catch (error) {
            console.error("Error creating reading list:", error);
            alert("Failed to create reading list.");
        }
    };

    const openModal = (listId) => {
        setSelectedListId(listId);
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
        setIsbn('');
    };

    const handleAddBookToList = async () => {
        if (!isbn.trim()) {
            alert("Please enter an ISBN.");
            return;
        }

        try {
            await axiosInstance.post(`/reading-list/${isbn}/${selectedListId}`);
            alert("Book added to reading list successfully!");
            closeModal();
        } catch (error) {
            console.error("Error adding book to reading list:", error);
            alert("Failed to add the book to the list.");
        }
    };




   const navigateToList = (listId, listName) => {
       router.push({
           pathname: `/list/${listId}`,
           query: { name: listName },
       });
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
            <h1>Your Reading Lists</h1>
            <p>Total reading lists: {totalLists}</p>

            <div style={{ marginBottom: '20px' }}>
                <input
                    type="text"
                    placeholder="Enter reading list name"
                    value={newListName}
                    onChange={(e) => setNewListName(e.target.value)}
                    style={{ marginRight: '10px', padding: '5px' }}
                />
                <button onClick={createReadingList}>Create Reading List</button>
            </div>

            <div style={{ marginBottom: '20px' }}>
                <label htmlFor="pageSize">Lists per page: </label>
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

            {readingLists.length === 0 ? (
                <p>No reading lists to display.</p>
            ) : (
                <div style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
                    gap: '20px'
                }}>
                    {readingLists.map((list) => (
                        <div key={list.id} style={{ border: '1px solid #ccc', padding: '10px' }}>
                            <h3>{list.name}</h3>
                            <button onClick={() => openModal(list.id)} style={{ marginRight: '5px' }}>Add Book</button>
                            <button onClick={() => navigateToList(list.id, list.name)}>Show List</button>
                        </div>
                    ))}
                </div>
            )}

            <div style={{ marginTop: '20px', display: 'flex', justifyContent: 'space-between' }}>
                <button onClick={previousPage} disabled={page === 0}>Previous</button>
                <span>Page {page + 1} of {totalPages}</span>
                <button onClick={nextPage} disabled={page === totalPages - 1}>Next</button>
            </div>

            {/* Modal for adding book to a reading list */}
            {showModal && (
                <div style={{
                    position: 'fixed',
                    top: 0,
                    left: 0,
                    right: 0,
                    bottom: 0,
                    backgroundColor: 'rgba(0, 0, 0, 0.5)',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center'
                }}>
                    <div style={{
                        backgroundColor: 'white',
                        padding: '20px',
                        borderRadius: '8px',
                        width: '300px',
                        textAlign: 'center'
                    }}>
                        <h2>Add Book to List</h2>
                        <input
                            type="text"
                            placeholder="Enter ISBN"
                            value={isbn}
                            onChange={(e) => setIsbn(e.target.value)}
                            style={{ marginBottom: '10px', width: '100%', padding: '8px' }}
                        />
                        <div>
                            <button onClick={handleAddBookToList} style={{ marginRight: '10px' }}>Add Book</button>
                            <button onClick={closeModal}>Cancel</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
