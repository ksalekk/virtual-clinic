import axios from "axios";
import React, { useState } from "react";
import { replaceUndefined } from "../utils";
import { apiUrl } from "../utils";
import ModalInfo from "../generics/ModalInfo";

const PatientEdit = () => {
    const patientId = JSON.parse(localStorage.getItem("user")).id;
    const patientDetailsUrl = `${apiUrl}/patients/${patientId}/details`;
    const [patient, setPatient] = useState();

    useState(async () => {
        try {
            const resp = await axios.get(patientDetailsUrl);
            const patientDetails = replaceUndefined(resp.data);
            setPatient(patientDetails);

        } catch (err) {
            console.error(err)
        }
    }, []);



    const handlePatientChange = (e) => {
        const { name, value } = e.target;
        setPatient({ ...patient, [name]: value });
    };

    const handleAddressChange = (e) => {
        const { name, value } = e.target;
        setPatient({
            ...patient,
            address: { ...patient.address, [name]: value }
        })
    };



    const applyChangesUrl = `${apiUrl}/patients/${patientId}`;
    const [applyFlag, setApplyFlag] = useState(false)

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(patient);
        axios.put(applyChangesUrl, patient)
            .then((resp) => setApplyFlag(true))
            .catch((err) => console.error(err));
    };



    if (!patient) {
        return <h2>Loading...</h2>;
    }

    return (
        <div className="patient-edit">
            {applyFlag && <ModalInfo 
                            header='Changes applied'
                            onClose={() => setApplyFlag(false)}/>}

            <h2>Patient Edit</h2>
            <form onSubmit={handleSubmit}>
                <div className="row">
                    <div className="col">
                        <h3>Personal</h3>
                        <hr />

                        <div>
                            <label htmlFor="firstname">Firstname</label>
                            <input
                                id="firstname"
                                type="text"
                                name="firstname"
                                value={patient.firstname}
                                onChange={handlePatientChange}
                            />
                        </div>

                        <div>
                            <label htmlFor="lastname">Lastname</label>
                            <input
                                id="lastname"
                                type="text"
                                name="lastname"
                                value={patient.lastname}
                                onChange={handlePatientChange}
                            />
                        </div>

                        <div>
                            <label htmlFor="dob">Date of Birth</label>
                            <input
                                id="dob"
                                type="date"
                                name="dateOfBirth"
                                value={patient.dateOfBirth}
                                onChange={handlePatientChange}
                            />
                        </div>

                        <div>
                            <label htmlFor="pesel">PESEL</label>
                            <input
                                id="pesel"
                                type="text"
                                name="pesel"
                                value={patient.pesel}
                                onChange={handlePatientChange}
                            />
                        </div>

                        <div>
                            <label htmlFor="gender">Gender</label>
                            <select
                                id="gender"
                                value={patient.gender}
                                name="gender"
                                onChange={handlePatientChange}>
                                <option value="male">Male</option>
                                <option value="female">Female</option>
                                <option value="other">Other</option>
                            </select>
                        </div>

                        <div>
                            <label htmlFor="phone">Phone Number</label>
                            <input
                                id="phone"
                                type="text"
                                name="phoneNumber"
                                value={patient.phoneNumber}
                                onChange={handlePatientChange}
                            />
                        </div>
                    </div>


                    <div className="col">
                        <h3>Address</h3>
                        <hr />

                        <div>
                            <label htmlFor="country">Country</label>
                            <input
                                id="country"
                                type="text"
                                name="country"
                                value={patient.address.country}
                                onChange={handleAddressChange}
                            />
                        </div>

                        <div>
                            <label htmlFor="city">City</label>
                            <input
                                id="city"
                                type="text"
                                name="city"
                                value={patient.address.city}
                                onChange={handleAddressChange}
                            />
                        </div>

                        <div>
                            <label htmlFor="street">Street</label>
                            <input
                                id="street"
                                type="text"
                                name="street"
                                value={patient.address.street}
                                onChange={handleAddressChange}
                            />
                        </div>

                        <div>
                            <label htmlFor="postcode">Post Code</label>
                            <input
                                id="postcode"
                                type="text"
                                name="postCode"
                                value={patient.address.postCode}
                                onChange={handleAddressChange}
                            />
                        </div>

                        <div className="corner">
                            <button type="submit">Save</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
};

export default PatientEdit;
