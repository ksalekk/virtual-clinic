package com.jsalek.pw.virtualclinic.domain.doctor;

import com.jsalek.pw.virtualclinic.domain.appointment.Appointment;
import com.jsalek.pw.virtualclinic.security.user.Role;
import com.jsalek.pw.virtualclinic.security.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = Role.Ordinals.DOCTOR)
public class Doctor extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstname;
    private String lastname;
    @Column(name = "license_number")
    private String licenseNumber;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String speciality;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

}
