package com.jsalek.pw.virtualclinic.domain.appointment;

import com.jsalek.pw.virtualclinic.domain.doctor.DoctorDto;
import com.jsalek.pw.virtualclinic.domain.patient.PatientDetailsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentDto {

    private Long id;
    private LocalDateTime datetime;
    private AppointmentStatus status;
    private String summary;
    private String treatmentPlan;
    private PatientDetailsDto patient;
    private DoctorDto doctor;

}
