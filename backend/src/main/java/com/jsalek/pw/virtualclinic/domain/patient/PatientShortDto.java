package com.jsalek.pw.virtualclinic.domain.patient;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientShortDto {
    Long id;
    String firstname;
    String lastname;
    String pesel;
}
