package com.jsalek.pw.virtualclinic.domain.doctor;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String licenseNumber;
    private String phoneNumber;
    private String speciality;

}
