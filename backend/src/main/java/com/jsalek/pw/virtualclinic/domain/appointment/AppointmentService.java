package com.jsalek.pw.virtualclinic.domain.appointment;

import com.jsalek.pw.virtualclinic.domain.doctor.Doctor;
import com.jsalek.pw.virtualclinic.domain.doctor.DoctorService;
import com.jsalek.pw.virtualclinic.domain.patient.Patient;
import com.jsalek.pw.virtualclinic.domain.patient.PatientService;
import com.jsalek.pw.virtualclinic.security.user.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            ModelMapper modelMapper,
            PatientService patientService,
            DoctorService doctorService) {

        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }


    public List<AppointmentShortDto> getAppointments(Long id, Role targetEntity) {
        //        Principal principal = (Principal) httpSession.getAttribute("principal");

        List<Appointment> appointments = (targetEntity == Role.PATIENT) ?
            this.appointmentRepository.findByPatientId(id) : this.appointmentRepository.findByDoctorId(id);

        return appointments
                .stream()
                .map(pojo -> this.modelMapper.map(pojo, AppointmentShortDto.class))
                .toList();
    }


    public Optional<AppointmentDto> getAppointmentDetails(Long id, Long appointmentId, Role targetEntity) {
        Optional<Appointment> appointment = (targetEntity == Role.PATIENT) ?
                this.appointmentRepository.findByPatientIdAndAppointmentId(id, appointmentId)
                : this.appointmentRepository.findByDoctorIdAndAppointmentId(id, appointmentId);

        return appointment.map( pojo -> this.modelMapper.map(pojo, AppointmentDto.class) );
    }


    public Optional<AppointmentShortDto> addAppointment(AppointmentShortDto appointment) throws HttpClientErrorException {
        if(appointment.getDoctorId() == null || appointment.getPatientId() == null) {
            return Optional.empty();
        }

        Optional<Patient> patient = this.patientService.getPatientEntity(appointment.getPatientId());
        Optional<Doctor> doctor = this.doctorService.getDoctorEntity(appointment.getDoctorId());
        if(patient.isEmpty() || doctor.isEmpty()) {
            return Optional.empty();
        }

        Appointment createdAppointment = this.modelMapper.map(appointment, Appointment.class);
        createdAppointment.setPatient(patient.get());
        createdAppointment.setDoctor(doctor.get());
        createdAppointment.setDatetime(appointment.getDatetime());
        createdAppointment.setStatus(AppointmentStatus.PENDING);

        return Optional.of(this.modelMapper.map(this.appointmentRepository.save(createdAppointment), AppointmentShortDto.class));
    }


    public Optional<AppointmentDto> updateAppointment(AppointmentDto updatedAppointment, Long appointmentId) {
        Optional<Appointment> appointment = this.appointmentRepository.findById(appointmentId);

        return appointment.map(
                pojo -> {
                    pojo.setSummary(updatedAppointment.getSummary());
                    pojo.setTreatmentPlan(updatedAppointment.getTreatmentPlan());
                    pojo.setStatus(AppointmentStatus.COMPLETED);
                    return this.modelMapper.map(this.appointmentRepository.save(pojo), AppointmentDto.class);
        });
    }


}
