package com.jsalek.pw.virtualclinic.domain.appointment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    @Query("SELECT a FROM Appointment a WHERE a.patient.id = ?1")
    List<Appointment> findByPatientId(Long patientId);
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = ?1")
    List<Appointment> findByDoctorId(Long doctorId);

    @Query("SELECT a FROM Appointment a WHERE a.patient.id = ?1 AND a.id = ?2")
    Optional<Appointment> findByPatientIdAndAppointmentId(Long patientId, Long appointmentId);
    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = ?1 AND a.id = ?2")
    Optional<Appointment> findByDoctorIdAndAppointmentId(Long doctorId, Long appointmentId);


}
