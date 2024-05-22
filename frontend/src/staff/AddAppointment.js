import React, { useEffect, useState } from "react";
import ModalList from "../generics/ModalList";
import axios from "axios";
import ModalInfo from "../generics/ModalInfo";

const AddAppointment = () => {
    const [modalInfo, setModalInfo] = useState();

    const TIMEZONE_OFFSET = new Date().getTimezoneOffset() * 60000;
    const [datetime, setDatetime] = useState();

    const doctorsUrl = `/doctors`;
    const [doctors, setDoctors] = useState();
    const [selectedDoctor, setSelectedDoctor] = useState();
    const [doctorModal, setDoctorModal] = useState(false);

    const patientsUrl = `/patients`;
    const [patients, setPatients] = useState();
    const [selectedPatient, setSelectedPatient] = useState();
    const [patientModal, setPatientModal] = useState(false);

    useEffect(() => {
        async function fetchData() {
            try {
                const [patientsResp, doctorsResp] = await Promise.all(
                    [axios.get(patientsUrl), axios.get(doctorsUrl)]
                )

                setPatients(patientsResp.data.map((item) => {
                    const id = item.id;
                    const name = `${item.firstname} ${item.lastname}`;
                    return { id, name };
                }));

                setDoctors(doctorsResp.data.map((item) => {
                    const id = item.id;
                    const name = `${item.firstname} ${item.lastname}`;
                    return { id, name };
                }));

            } catch (err) {
                console.error("Error fetching doctors/patients data: ", err)
            }
        }

        setDatetime(new Date(Date.now() - TIMEZONE_OFFSET).toISOString().slice(0, -8))
        fetchData()
    }, [TIMEZONE_OFFSET, doctorsUrl, patientsUrl]);


    const postAppointmentUrl = `/appointments`;

    const addAppointment = (e) => {
        e.preventDefault();

        if(!selectedDoctor || !selectedPatient) {
            setModalInfo({
                header: 'Empty patient and/or patient',
                message: 'You must select patient and doctor before you post new appointment'
            })
            return;
        }

        const appointment = {
            datetime: datetime,
            patientId: selectedPatient.id,
            doctorId: selectedDoctor.id
        }

        axios.post(postAppointmentUrl, appointment)
            .then((resp) => setModalInfo({header: 'Appointment has been added!'}))
            .catch((err) => console.error(err));
    }


    if (!doctors || !patients) {
        return <h2>Loading...</h2>;
    }

    return (
        <section className="add-appointment">
            <h2>New Appointment</h2>
            <form onSubmit={addAppointment}>

                <div className="row">
                    <div className="col">
                        <div>
                            <input
                                name="datetime"
                                type="datetime-local"
                                value={datetime}
                                onChange={(e) => { setDatetime(e.target.value) }}
                            />
                        </div>

                        <div>
                            <button type='button' onClick={() => setDoctorModal(true)}>
                                Select doctor
                            </button>
                        </div>

                        <div>
                            <button type='button' onClick={() => setPatientModal(true)}>
                                Select patient
                            </button>
                        </div>
                    </div>

                    <div className="col">
                        <p><span>Appointment datetime</span>{datetime.replace('T', ' ')}</p>
                        <p><span>Doctor </span>{selectedDoctor && selectedDoctor.name}</p>
                        <p><span>Patient </span>{selectedPatient && selectedPatient.name}</p>
                    </div>
                </div>
                
                <button type="submit">Save</button>
            </form>


            {modalInfo &&
            <ModalInfo header={modalInfo.header} message={modalInfo.message}  onClose={() => setModalInfo()}/>}           


            {doctorModal && (
                <ModalList
                    items={doctors}
                    onClose={() => setDoctorModal(false)}
                    onSelectItem={(item) => {
                        setSelectedDoctor(item);
                        setDoctorModal(false);
                    }}
                />
            )}

            {patientModal && (
                <ModalList
                    items={patients}
                    onClose={() => setPatientModal(false)}
                    onSelectItem={(item) => {
                        setSelectedPatient(item);
                        setPatientModal(false);
                    }}
                />
            )}
            
        </section >
    );
};

export default AddAppointment;