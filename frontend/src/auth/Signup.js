import axios from 'axios';
import './Auth.css'
import React, { useState } from 'react'
import { Link } from 'react-router-dom';
import ModalInfo from '../generics/ModalInfo';

const Signup = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [invalidSignup, setInvalidSignup] = useState(false);
    const [success, setSuccess] = useState(false);

    const signupUrl = `/auth/signup`

    const handleSignup = async (e) => {
        e.preventDefault();
        setInvalidSignup(false);

        try {
            const resp = await axios.post(signupUrl, { username, password });
            const user = resp.data;
            localStorage.setItem('user', JSON.stringify(user));
            setSuccess(true);

        } catch (err) {
            console.error('Error signup: ', err);
            err.response.status == 422 && setInvalidSignup(true);
        }
    }


    return (
        <div className="layout centered-layout">
            { success &&
                <ModalInfo header={ 'Patient account was created!' } message={ 'Your account was created. You can login now.' }  onClose={() => window.location.assign('/login')}/>
            }


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

                    {invalidSignup &&
                        <p className="error-message">Given username is forbidden. Try another one</p>
                    }

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