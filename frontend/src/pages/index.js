import Link from 'next/link';
import styles from './index.module.css'; // Optional for custom styling

export default function Home() {
    return (
        <div className={styles.container}>
            <h1>Welcome to Booky!</h1>
            <p>Manage your book lists and track your reading journey.</p>
            <div className={styles.buttonContainer}>
                <Link href="/login">
                    <button className={styles.button}>Login</button>
                </Link>
                <Link href="/register">
                    <button className={styles.button}>Register</button>
                </Link>
            </div>
        </div>
    );
}
