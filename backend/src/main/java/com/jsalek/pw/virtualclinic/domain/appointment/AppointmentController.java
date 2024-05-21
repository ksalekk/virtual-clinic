package com.jsalek.pw.virtualclinic.domain.appointment;


import com.jsalek.pw.virtualclinic.security.authorization.AuthGuard;
import com.jsalek.pw.virtualclinic.security.user.Principal;
import com.jsalek.pw.virtualclinic.security.user.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AuthGuard authGuard;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, AuthGuard authGuard) {
        this.appointmentService = appointmentService;
        this.authGuard = authGuard;
    }


    @GetMapping("/patients/{id}/appointments")
    public ResponseEntity<List<AppointmentShortDto>> getPatientAppointments(@PathVariable Long id, HttpSession httpSession) {
        // patient => only own
        // doctor => cannot
        // staff => cannot

        // GET /patients/{id}/appointments => filter pass only PATIENT; additional condition must be true: principal.id == id
        Principal principal = (Principal) httpSession.getAttribute("principal");
        if(this.authGuard.checkIdorOccurence(principal, Role.PATIENT, id)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(this.appointmentService.getAppointments(id, Role.PATIENT));
        }
    }


    @GetMapping("/doctors/{id}/appointments")
    public ResponseEntity<List<AppointmentShortDto>> getDoctorAppointments(@PathVariable Long id, HttpSession httpSession) {
        // patient => cannot
        // doctor => only own
        // staff => can

        // GET /doctors/{id}/appointments => filter pass only DOCTOR; additional condition must be true: principal.id == id
        Principal principal = (Principal) httpSession.getAttribute("principal");
        if(this.authGuard.checkIdorOccurence(principal, Role.DOCTOR, id)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(this.appointmentService.getAppointments(id, Role.DOCTOR));
        }
    }


    @GetMapping("/patients/{patient_id}/appointments/{appointment_id}")
    public ResponseEntity<AppointmentDto> getPatientAppointment(
            @PathVariable(name = "patient_id") Long patientId,
            @PathVariable(name = "appointment_id") Long appointmentId,
            HttpSession httpSession) {

        // patient => only own
        // doctor => cannot
        // staff => cannot

        // GET /patients/{id}/appointments => filter pass only PATIENT; additional condition must be true: principal.id == id
        Principal principal = (Principal) httpSession.getAttribute("principal");
        if(this.authGuard.checkIdorOccurence(principal, Role.PATIENT, patientId)) {
            return ResponseEntity.notFound().build();
        } else {
            return this.appointmentService.getAppointmentDetails(patientId, appointmentId, Role.PATIENT)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }


    @GetMapping("/doctors/{doctor_id}/appointments/{appointment_id}")
    public ResponseEntity<AppointmentDto> getDoctorAppointment(
            @PathVariable(name = "doctor_id") Long doctorId,
            @PathVariable(name = "appointment_id") Long appointmentId,
            HttpSession httpSession) {
        // patient => cannot
        // doctor => only own
        // staff => cannot

        // GET /doctors/{id}/appointments => filter pass only DOCTOR; additional condition must be true: principal.id == id
        Principal principal = (Principal) httpSession.getAttribute("principal");
        if(this.authGuard.checkIdorOccurence(principal, Role.DOCTOR, doctorId)) {
            return ResponseEntity.notFound().build();
        } else {
            return this.appointmentService.getAppointmentDetails(doctorId, appointmentId, Role.DOCTOR)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }


    // TODO: handling exception when patient and doctor id are not correct
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentShortDto> addAppointment(@RequestBody AppointmentShortDto appointment) {
        // patient => cannot
        // doctor => cannot
        // staff => can

        // GET patients => restricted to STAFF role (filtering by role is enough)
        return this.appointmentService.addAppointment(appointment)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }


    @PutMapping("/doctors/{doctor_id}/appointments/{appointment_id}")
    public ResponseEntity<AppointmentDto> updateAppointment(
            @RequestBody AppointmentDto appointment,
            @PathVariable(name = "doctor_id") Long doctorId,
            @PathVariable(name = "appointment_id") Long appointmentId,
            HttpSession  httpSession) {
        // patient => cannot
        // doctor => only own
        // staff => cannot

        // PUT /appointments/id => filter pass only DOCTOR; additional condition must be true: principal.id == id
        Principal principal = (Principal) httpSession.getAttribute("principal");
        if(this.authGuard.checkIdorOccurence(principal, Role.DOCTOR, doctorId)) {
            return ResponseEntity.notFound().build();
        } else {
            return this.appointmentService.updateAppointment(appointment, appointmentId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }



}
