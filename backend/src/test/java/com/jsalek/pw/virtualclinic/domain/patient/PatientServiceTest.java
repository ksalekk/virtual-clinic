package com.jsalek.pw.virtualclinic.domain.patient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

public class PatientServiceTest {

    private static ModelMapper modelMapper;

    @BeforeAll
    public static void beforeAll() {
        modelMapper = new ModelMapper();

    }

    @Test
    public void shouldMapPatientToPatientDto() {
        PatientDetailsDto patientDetailsDto = new PatientDetailsDto(
                1L,
                "John",
                "Doe",
                LocalDate.now(),
                "12345678910",
                "male",
                "123-456-789",
                new Address()
        );

        Patient patient = modelMapper.map(patientDetailsDto, Patient.class);
    }
}
