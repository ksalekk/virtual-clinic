import React from 'react';
import './generics.css';

const ModalList = ({ items, onClose, onSelectItem }) => {


    return (
        <div className="modal-overlay">
            <div className="modal">
                <header>
                    <h2>Chose an element</h2>
                    <button onClick={onClose}>X</button>
                </header>
                <div className="modal-body">
                    <ul>
                        {items.map(item => (
                            <li key={item.id} onClick={() => onSelectItem(item)}>
                                {item.name}
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default ModalList;