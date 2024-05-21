import React, { useEffect, useState } from 'react'
import { Routes, Route } from 'react-router-dom';
import NotFound from '../generics/NotFound';
import AppointmentDetails from '../appointments/AppointmentDetails';
import axios from 'axios';
import PatientAppointments from './PatientAppointments';
import Header from '../generics/Header';
import PatientEdit from './PatientEdit';
import { apiUrl } from '../utils';

const PatientArea = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    const patientUrl = `${apiUrl}/patients/${user.id}`;
    const [patient, setPatient] = useState();

    useEffect(() => {
        axios.get(patientUrl)
            .then(resp => setPatient(resp.data))
            .catch(err => console.error('Error fetching patient data: ', err));
    }, [patientUrl]);


    const header = [
        { name: 'Home', path: '/patient' },
        { name: 'Edit Profile', path: '/patient/edit' },
        { name: 'Appointments', path: '/patient/appointments' },
        { name: 'Logout', path: '/logout' },
    ]


    if (!patient) {
        return <></>;
    }

    return (
        <div className='layout dashboard-layout'>
            <div className="dashboard">
                <Header headerLinks={header} />
                <hr />
                <main className="content">
                    <Routes>
                        <Route path='/' element={<h1>Hello {patient.firstname}!</h1>}></Route>
                        <Route path='/edit' element={<PatientEdit />}></Route>
                        <Route path='/appointments' element={<PatientAppointments patientId={patient.id} />}></Route>
                        <Route path='/appointments/:appointmentId' element={<AppointmentDetails />}></Route>
                        <Route path="*" element={<NotFound />} />
                    </Routes>
                </main>
            </div>
        </div>
    )
}

export default PatientArea