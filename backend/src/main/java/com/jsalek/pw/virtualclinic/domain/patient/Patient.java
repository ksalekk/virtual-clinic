package com.jsalek.pw.virtualclinic.domain.patient;

import com.jsalek.pw.virtualclinic.domain.appointment.Appointment;
import com.jsalek.pw.virtualclinic.global.ResourceEntity;
import com.jsalek.pw.virtualclinic.security.user.Role;
import com.jsalek.pw.virtualclinic.security.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter @Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = Role.Ordinals.PATIENT)
public class Patient extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private String pesel;
    private String gender;
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address = new Address();

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

}
