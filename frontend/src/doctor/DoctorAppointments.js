import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import ItemsList from '../generics/ItemsList';
import { formatDatetime } from '../utils';
import { ViewIcon, EditIcon } from '../icons';

const DoctorAppointments = ( {restrict} ) => {
    const user = JSON.parse(localStorage.getItem('user'));
    const { id: pathId } = useParams();
    const id = pathId ?? user.id;
    const appointmentsUrl = `/doctors/${id}/appointments`;
    const [appointments, setAppointments] = useState();

    
    useEffect(() => {
        axios.get(appointmentsUrl)
            .then((resp) => {
                const appointments = resp.data.map((item) => {
                    return {...item, datetime: formatDatetime(item.datetime)}
                });
                setAppointments(appointments);
            })
            .catch(err => console.error('Error fetching doctor appointments data: ', err));
    }, [appointmentsUrl]);


    const navigate = useNavigate();
    const showAppointmentDetails = (id) => navigate(`/doctor/appointments/${id}`)
    const completeAppointmentDetails = (id) => navigate(`/doctor/appointments/${id}/edit`)

    const actions = restrict ? false : [
        { handler: showAppointmentDetails, icon: ViewIcon },
        { handler: completeAppointmentDetails, icon: EditIcon },
    ]

    const tableStructure = {
        data: appointments,
        cols: [
            { property: 'datetime', name: 'Datetime' },
            { property: 'status', name: 'Status' }
        ],
        actions: actions
    }
    
    return (
    <section>
        <h2>Appointments</h2>
        <ItemsList tableStructure={tableStructure}/>
    </section>
    )

}

export default DoctorAppointments