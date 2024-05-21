package com.jsalek.pw.virtualclinic.domain.patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDetailsDto {

    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String pesel;
    private String gender;
    private String phoneNumber;
    private Address address;

}
