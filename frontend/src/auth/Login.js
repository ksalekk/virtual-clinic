import './Auth.css';
import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [invalidAuth, setInvalidAuth] = useState(false);

    const loginUrl = `/auth/login`


    const handleLogin = async (e) => {
        console.log(loginUrl)
        e.preventDefault();
        setInvalidAuth(false);

        const auth = btoa(`${username}:${password}`);

        try {
            const resp = await axios.get(loginUrl, {
                headers: { 'Authorization': ` Basic ${auth}` }
            });
            const user = resp.data;
            localStorage.setItem('user', JSON.stringify(user));
            window.location.assign(`/${user.role.toLowerCase()}`);

        } catch (err) {
            console.error('Error authentication: ', err);
            err.response.status == 400 && setInvalidAuth(true);
        }

    }


    return (
        <div className='layout centered-layout'>
            <section className="auth-container">
                <h2>Login</h2>
                <form onSubmit={handleLogin} autoComplete="off">
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

                    {invalidAuth &&
                        <p className="error-message">Invalid credentials</p>
                    }

                    <button type="submit">Login</button>
                </form>

                <hr />

                <nav>
                    <Link to='/signup'>Create an account here</Link>
                </nav>
            </section>
        </div>
    );
};


export default Login;
