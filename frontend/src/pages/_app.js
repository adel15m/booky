import "../globals.css"; // Import global styles

// Define the main App component and export it as default
function MyApp({ Component, pageProps }) {
    return <Component {...pageProps} />;
}

export default MyApp;
