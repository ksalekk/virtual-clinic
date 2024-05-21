package com.jsalek.pw.virtualclinic.domain.staff;

import com.jsalek.pw.virtualclinic.global.ResourceEntity;
import com.jsalek.pw.virtualclinic.security.user.Role;
import com.jsalek.pw.virtualclinic.security.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = Role.Ordinals.STAFF)
public class Staff extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

}
