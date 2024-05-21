import React, { useEffect, useState } from 'react'
import ItemsList from '../generics/ItemsList'
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { formatDatetime, apiUrl } from '../utils';
import { ViewIcon } from '../icons';

const PatientAppointments = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    const appointmentsUrl = `${apiUrl}/patients/${user.id}/appointments`;
    const [appointments, setAppointments] = useState();

    useEffect(() => {
        axios.get(appointmentsUrl)
            .then((resp) => {
                const appointments = resp.data.map((item) => {
                    return {...item, datetime: formatDatetime(item.datetime)}
                });
                setAppointments(appointments);
            })
            .catch(err => console.error('Error fetching patient appointments data: ', err));
    }, [appointmentsUrl]);


    const navigate = useNavigate();
    const showAppointmentDetails = (id) => navigate(`/patient/appointments/${id}`)
    
    const tableStructure = {
        data: appointments,
        cols: [
            { property: 'datetime', name: 'Datetime' },
            { property: 'status', name: 'Status' }
        ],
        actions: [
            { handler: showAppointmentDetails, icon: ViewIcon }
        ]
    }

    return (
        <section>
            <h2>Appointments</h2>
            <ItemsList tableStructure={tableStructure}/>
        </section>
    )
}

export default PatientAppointments