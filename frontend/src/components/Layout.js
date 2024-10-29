// src/components/Layout.js
import { useRouter } from 'next/router';
import Navbar from './Navbar';

const Layout = ({ children }) => {
    const router = useRouter();
    const showNavbar = router.pathname !== '/';

    return (
        <div>
            {showNavbar && <Navbar />}
            <main>{children}</main>
        </div>
    );
};

export default Layout;
