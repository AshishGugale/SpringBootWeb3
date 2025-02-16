import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [data, setData] = useState(null);

    useEffect(() => {
      fetch("http://localhost:8080/")
        .then((response) => response.json()) // Parse JSON response
        .then((data) => setData(data)) // Log the parsed data
        .catch((error) => console.error("Error fetching data:", error));
    }, []);

    return <div>{data ? (data.message) : "Loading..."}</div>;
}

export default App
