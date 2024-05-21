import React from 'react'
import Header from '../generics/Header';
import { Route, Routes } from 'react-router-dom';
import NotFound from '../generics/NotFound';
import DoctorsList from './DoctorsList';
import DoctorAppointments from '../doctor/DoctorAppointments';
import AddAppointment from './AddAppointment';

const StaffArea = () => {
	const user = JSON.parse(localStorage.getItem('user'));

    const header = [
        {name: 'Home', path: '/staff'},
        {name: 'Add appointment', path: '/staff/new-appointment'},
        {name: 'Check doctors', path: '/staff/doctors'},
        {name: 'Logout', path: '/logout'},
    ]

    // if (!staff) {
        // return <></>;;
    // }


	return (
		<div className="layout dashboard-layout">
		<div className='dashboard'>
			<Header headerLinks={header} />
			<hr />
			<main className="content">
				<Routes>
					<Route path='/' element={<h1>Hello!</h1>}></Route>
					<Route path='/doctors' element={<DoctorsList />}></Route>
					<Route path='/doctors/:id/appointments' element={<DoctorAppointments restrict={true} />}></Route>
					<Route path='/new-appointment' element={<AddAppointment />}></Route>
					<Route path="*" element={<NotFound />} />
				</Routes>
			</main>
		</div>
		</div>

	)
}


export default StaffArea