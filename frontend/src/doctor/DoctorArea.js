import React, { useEffect, useState } from 'react'
import { Route, Routes } from 'react-router-dom';
import NotFound from '../generics/NotFound';
import AppointmentDetails from '../appointments/AppointmentDetails';
import axios from 'axios';
import DoctorAppointments from './DoctorAppointments';
import Header from '../generics/Header';

const DoctorArea = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    const doctorUrl = `/doctors/${user.id}`;
    const [doctor, setDoctor] = useState();

    useEffect(() => {
        axios.get(doctorUrl)
            .then(resp => setDoctor(resp.data))
            .catch(err => console.error('Error fetching doctor data: ', err));
    }, [doctorUrl]);


    const header = [
        { name: 'Home', path: '/doctor' },
        { name: 'Your Appointments', path: '/doctor/appointments' },
        { name: 'Logout', path: '/logout' },
    ];


    if (!doctor) {
        return <></>;;
    }

    return (
        <div className="layout dashboard-layout">
            <div className='dashboard'>
                <Header headerLinks={header} />
                <hr />
                <main className="content">
                    <Routes>
                        <Route path='/' element={<h1>Hello {doctor.firstname}!</h1>}></Route>
                        <Route path='/appointments' element={<DoctorAppointments />}></Route>
                        <Route path='/appointments/:appointmentId' element={<AppointmentDetails />}></Route>
                        <Route path='/appointments/:appointmentId/edit' element={<AppointmentDetails editSummary={true} />}></Route>
                        <Route path="*" element={<NotFound />} />
                    </Routes>
                </main>
            </div>
        </div>
    )
}

export default DoctorArea