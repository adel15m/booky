import { useState } from 'react';
import axios from 'axios';
import { useRouter } from 'next/router';

export default function Home() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const router = useRouter();

    const handleLogin = async () => {
        try {
            const response = await axios.post(`http://localhost:8080/api/user/login`, { username, password });
            const token = response.data;
            if (token) {
                localStorage.setItem('jwtToken', token);
                router.push('/books?page=0&size=6');
            } else {
                alert("Login failed: no token received");
            }
        } catch (error) {
            console.error("Error during login:", error);
            alert("Login failed.");
        }
    };

    const handleRegister = async () => {
        try {
            await axios.post(`http://localhost:8080/api/user/register`, { username, password });
            alert("Registration successful! You can now log in.");
        } catch (error) {
            console.error("Error during registration:", error);
            alert("Registration failed.");
        }
    };

    return (
        <div style={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            height: '100vh',
            textAlign: 'center',
            padding: '20px'
        }}>
            <h1>Welcome to Booky!</h1>
            <p>Manage your book lists and track your reading journey.</p>

            {/* Username and Password Input Fields */}
            <div style={{ marginTop: '20px', width: '100%', maxWidth: '400px' }}>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    style={{ marginBottom: '10px', width: '100%', padding: '10px' }}
                    required
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    style={{ marginBottom: '10px', width: '100%', padding: '10px' }}
                    required
                />
            </div>

            {/* Login and Register Buttons */}
            <div style={{ marginTop: '10px' }}>
                <button onClick={handleLogin} style={{ marginRight: '10px' }}>Login</button>
                <button onClick={handleRegister}>Register</button>
            </div>
        </div>
    );
}

