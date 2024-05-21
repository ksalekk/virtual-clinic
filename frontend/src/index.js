import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { BrowserRouter } from 'react-router-dom';
import axios from 'axios';
import { apiUrl } from './utils';

// allows to use cookies
axios.defaults.withCredentials = true;
console.log("BEFORE")
console.log('apiUrl', apiUrl)
console.log('env', process.env.REACT_APP_API_URL)
axios.defaults.baseURL = apiUrl;
console.log("AFTER")
console.log('apiUrl', apiUrl)
console.log('env', process.env.REACT_APP_API_URL)




const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
	// <React.StrictMode>
		<BrowserRouter>
			<App />
		 </BrowserRouter>
	// </React.StrictMode>
);

