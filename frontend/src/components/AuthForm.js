// src/components/AuthForm.js
import { useState } from 'react';
import axios from 'axios';
import { useRouter } from 'next/router';

const AuthForm = ({ type }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const router = useRouter();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const endpoint = type === 'register' ? '/user/register' : '/user/login';
            const response = await axios.post(`http://localhost:8080/api${endpoint}`, { username, password });

            if (type === 'login') {
                const token = response.data; // Adjust to receive token directly as the response body
                if (token) {
                    localStorage.setItem('jwtToken', token); // Store token in localStorage
                    router.push('/books?page=0&size=6'); // Redirect to books page after login
                } else {
                    alert("Login failed: no token received");
                }
            } else {
                alert("Registration successful! You can now log in.");
                router.push('/login');
            }
        } catch (error) {
            console.error("Error during authentication:", error);
            alert("An error occurred.");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>{type === 'register' ? 'Register' : 'Login'}</h2>
            <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} placeholder="Username" required />
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" required />
            <button type="submit">{type === 'register' ? 'Register' : 'Login'}</button>
        </form>
    );
};

export default AuthForm;
