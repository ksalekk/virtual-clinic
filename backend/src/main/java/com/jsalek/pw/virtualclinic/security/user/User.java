package com.jsalek.pw.virtualclinic.security.user;

import com.jsalek.pw.virtualclinic.global.ResourceEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "app_user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_role", discriminatorType = DiscriminatorType.INTEGER)
public abstract class User extends ResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    @Column(name = "user_password")
    private String password;

//    @Column(name = "user_role")
    @Column(name = "user_role", insertable = false, updatable = false)
    private Role role;

}
