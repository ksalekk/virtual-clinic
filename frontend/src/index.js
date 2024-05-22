import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { BrowserRouter } from 'react-router-dom';
import axios from 'axios';
import { apiUrl } from './utils';

axios.defaults.withCredentials = true;
axios.defaults.baseURL = apiUrl;


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
	// <React.StrictMode>
		<BrowserRouter>
			<App />
		 </BrowserRouter>
	// </React.StrictMode>
);

