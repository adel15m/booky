// src/utils/axiosInstance.js
import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/api', // Ensure this matches your backend base URL
});

axiosInstance.interceptors.request.use(config => {
    const token = localStorage.getItem('jwtToken');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, error => {
    console.error("Request Error:", error);
    return Promise.reject(error);
});

axiosInstance.interceptors.response.use(response => response, error => {
    console.error("Response Error:", error); // Logs detailed error
    return Promise.reject(error);
});

export default axiosInstance;
