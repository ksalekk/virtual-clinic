import './Auth.css'
import React, { useState } from 'react'
import { Link } from 'react-router-dom';
import { apiUrl } from '../utils';

const Signup = () => {
    //TODO: finish signing up functionality
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const signupUrl = `${apiUrl}/auth/signup`

    const handleSignup = (e) => {
        e.preventDefault();
        console.log(`username: ${username}`)
        console.log(`password: ${password}`)
    }


    return (
        <div className="layout centered-layout">
            <section className="auth-container">
                <h2>Signup</h2>
                <form onSubmit={handleSignup} autoComplete="off">
                    <input
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />

                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />

                    <button type="submit">Signup</button>
                </form>

                <hr />

                <nav>
                    <Link to='/login'>Have an account? Login here</Link>
                </nav>
            </section>
        </div>
    );
};

export default Signup