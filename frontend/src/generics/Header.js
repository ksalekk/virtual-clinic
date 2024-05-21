import React from 'react'
import { Link } from 'react-router-dom'
import './generics.css'

const Header = ({ headerLinks }) => {

    return (
        <header>
            <nav>
                {headerLinks.map((item, idx) => (
                    <Link to={item.path} key={idx}> <button>{item.name}</button> </Link>
                ))}
            </nav>
        </header>
    )
}

export default Header