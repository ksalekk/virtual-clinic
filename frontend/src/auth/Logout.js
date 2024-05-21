import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { apiUrl } from '../utils';

const Logout = () => {
	const [logoutCompleted, setLogoutCompleted] = useState(false);
	const logoutUrl = `${apiUrl}/auth/logout`

	useEffect(() => {
		axios.get(logoutUrl)
			.then(() => setLogoutCompleted(true))
			.catch((err) => console.error(err));
	}, [logoutUrl]);


	return (
		<div className="layout centered-layout">
			<div className="info-window">

				<header><h2>Logout</h2></header>

				<section className="content">

					{logoutCompleted ? (<>
						<p>You are logged out</p>
						<hr />
						<a href="/">Return to home page</a>
					</>)
						:
						<p>Logout in progress...</p>}

				</section>
			</div>
		</div>
	);
}

export default Logout