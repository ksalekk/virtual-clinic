package com.jsalek.pw.virtualclinic.domain.appointment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentShortDto {

    private Long id;
    private LocalDateTime datetime;
    private AppointmentStatus status;
    private Long patientId;
    private Long doctorId;

}
