// src/components/Navbar.js
import { useRouter } from 'next/router';

const Navbar = () => {
    const router = useRouter();

    const handleLogout = () => {
        localStorage.removeItem('jwtToken'); // Delete token
        router.push('/'); // Redirect to home
    };

    return (
        <div style={{
            display: 'flex',
            justifyContent: 'flex-end',
            padding: '10px 20px',
            borderBottom: '1px solid #ccc'
        }}>
            <button onClick={() => router.push('/books')} style={{ marginRight: '10px' }}>Books</button>
            <button onClick={() => router.push('/reading-lists')} style={{ marginRight: '10px' }}>Reading List</button>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
};

export default Navbar;
