package com.jsalek.pw.virtualclinic.domain.appointment;

import com.jsalek.pw.virtualclinic.domain.doctor.Doctor;
import com.jsalek.pw.virtualclinic.domain.patient.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appointment_datetime")
    private LocalDateTime datetime;

    @Column(name = "appointment_status")
    private AppointmentStatus status;

    private String summary;

    @Column(name = "treatment_plan")
    private String treatmentPlan;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

}
