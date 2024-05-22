package com.jsalek.pw.virtualclinic.security.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupDto {
    private String username;
    private String password;
    private Role role;
}
