import './App.css';
import React from 'react'
import { Routes, Route } from 'react-router-dom'
import Login from './auth/Login'
import Signup from './auth/Signup'
import PatientArea from './patient/PatientArea'
import DoctorArea from './doctor/DoctorArea'
import NotFound from './generics/NotFound'
import Logout from './auth/Logout';
import StaffArea from './staff/StaffArea';

function App() {
  return (
    <div className="App">
        <Routes>
          <Route path='/' element={<Login />} />
          <Route path='/login' element={<Login />} />
          <Route path='/signup' element={<Signup />} />
          <Route path='/logout' element={<Logout />} />
          <Route path='/patient/*' element={<PatientArea />} />
          <Route path='/doctor/*' element={<DoctorArea />} />
          <Route path='/staff/*' element={<StaffArea />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
    </div>
  );
}

export default App;
