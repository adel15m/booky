// src/pages/_app.js
import "../globals.css"; // Import global styles
import { useRouter } from 'next/router';
import Navbar from '../components/Navbar';

function MyApp({ Component, pageProps }) {
    const router = useRouter();
    const showNavbar = router.pathname !== '/';

    return (
        <div>
            {showNavbar && <Navbar />}
            <Component {...pageProps} />
        </div>
    );
}

export default MyApp;
