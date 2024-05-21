import React from 'react';
import './generics.css';

const ModalInfo = ({ header, message, onClose }) => {

    return (
        <div className="modal-overlay">
            <div className="modal modal-info">
                <header><h2>{header}</h2></header>
                <div className="modal-body">
                    <p>{message}</p>
                    <button onClick={onClose}>Ok</button>
                </div>
            </div>
        </div>
    );
};

export default ModalInfo;