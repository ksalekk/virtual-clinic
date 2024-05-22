import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import { formatDatetime, replaceUndefined } from '../utils';
import ModalInfo from '../generics/ModalInfo';

const AppointmentDetails = ({ editSummary }) => {
    const [modalInfo, setModalInfo] = useState();

    const { appointmentId: id } = useParams();
    const user = JSON.parse(localStorage.getItem('user'));
    const target = `${user.role}s`.toLowerCase();
    const appointmentDetailsUrl = `/${target}/${user.id}/appointments/${id}`;

    const [appointment, setAppointment] = useState();
    const [patient, setPatient] = useState();
    const [address, setAddress] = useState();
    const [doctor, setDoctor] = useState();

    useEffect(() => {
        axios.get(appointmentDetailsUrl)
            .then(resp => {
                const appointment = replaceUndefined(resp.data, '');
                setAppointment(appointment);
                setPatient(appointment.patient);
                setAddress(appointment.patient.address);
                setDoctor(appointment.doctor);
            })
            .catch(err => console.error('Error fetching appointment details data: ', err));
    }, [appointmentDetailsUrl]);


    const handleAppointmentChange = (e) => {
        const { name, value } = e.target;
        setAppointment({ ...appointment, [name]: value });
    };


    const updateAppointmentUrl = `/doctors/${user.id}/appointments/${id}`;
    const updateAppointment = (e) => {
        e.preventDefault();

        axios.put(updateAppointmentUrl, { ...appointment })
            .then((resp) => setModalInfo({ header: 'Changes was applied!'}))
            .catch((err) => console.error(err))
    }


    if (!appointment) {
        return (<h3>Loading...</h3>);
    }

    return (
        <div className='appointment-details-container'>
            <h2>Appointment Details</h2>
            <section className='appointment-details-content'>
                <header>
                    <p><span>Datetime </span>{formatDatetime(appointment.datetime)}</p>
                    <p><span>Status </span>{appointment.status}</p>
                </header>

                <section className="personal">
                    <section className="patient-data">
                        <div>
                            <h3>Patient</h3>
                            <p><span>Name </span> {patient.firstname} {patient.lastname}</p>
                            <p><span>DOB </span>{patient.dateOfBirth}</p>
                            <p><span>PESEL</span>{patient.pesel} </p>
                            <p><span>Gender</span>{patient.gender} </p>
                        </div>

                        <div>
                            <h3> &nbsp; </h3>
                            <p><span>Phone Number </span>{patient.phoneNumber} </p>
                            <p><span>City </span>{address.city} </p>
                            <p><span>Street </span>{address.street} </p>
                            <p><span>Post Code </span>{address.postCode} </p>
                        </div>
                    </section>

                    <section className='doctor-data'>
                        <h3>Doctor</h3>
                        <p><span>Name </span> {doctor.firstname} {doctor.lastname}</p>
                        <p><span>License Number </span>{doctor.licenseNumber} </p>
                        <p><span>Phone Number </span>{doctor.phoneNumber} </p>
                        <p><span>Speciality </span>{doctor.speciality} </p>
                    </section>
                </section>

                <hr />

                <section className='summary'>
                    {editSummary ? 
                        <>
                        <h3>Edit summary</h3>
                        <form onSubmit={updateAppointment} className='edit-summary'>

                            <label htmlFor='summary'>Summary</label>
                            <textarea
                                id="summary"
                                name="summary"
                                value={appointment.summary}
                                onChange={handleAppointmentChange}
                            />

                            <label htmlFor='treatment'>Treatment plan</label>
                            <textarea
                                id="treatment"
                                name="treatmentPlan"
                                value={appointment.treatmentPlan}
                                onChange={handleAppointmentChange}
                            />
                            <button type="submit">Apply</button>
                        </form>
                        </>
                        :
                        <>
                        <h3>Summary</h3>
                        <p>{appointment.summary ? appointment.summary : '-'}</p>

                        <h3>Treatmen plan</h3>
                        <p>{appointment.treatmentPlan ? appointment.treatmentPlan : '-'}</p>
                        </>
                    }
                </section>
            </section>

            {modalInfo &&
            <ModalInfo header={modalInfo.header} message={modalInfo.message}  onClose={() => setModalInfo()}/>}   
        </div>
    )
}

export default AppointmentDetails