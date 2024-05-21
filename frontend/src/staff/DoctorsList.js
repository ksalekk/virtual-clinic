import React, { useEffect, useState } from 'react'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import ItemsList from '../generics/ItemsList';
import { apiUrl } from '../utils';
import { ViewIcon } from '../icons';

const DoctorsList = () => {
    const doctorsUrl = `${apiUrl}/doctors`;
    const [doctors, setDoctors] = useState();


    useEffect(() => {
        async function fetchData() {
            try {
                const resp = await axios.get(doctorsUrl);
                setDoctors(resp.data.map(doctor => {
                    return {...doctor, name: `${doctor.firstname} ${doctor.lastname}`}
                }))

            } catch (err) {
                console.error('Error fetching doctors data: ', err)
            }
        }

        fetchData();
    }, [doctorsUrl]);

    
    const navigate = useNavigate();
    const showAppointments = (id) => navigate(`/staff/doctors/${id}/appointments`);

    const tableStructure = {
        data: doctors,
        cols: [
            { property: 'name', name: 'Name' },
            { property: 'speciality', name: 'Speciality' },
            { property: 'licenseNumber', name: 'License number' }
        ],
        actions: [
            { handler: showAppointments, icon: ViewIcon }
        ]
    }


    return (
        <section>
            <h2>Doctors</h2>
            <ItemsList tableStructure={tableStructure}/>
        </section>
    )
}

export default DoctorsList