package com.jsalek.pw.virtualclinic.security.authentication;

import com.jsalek.pw.virtualclinic.security.user.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpDto {
    @NotEmpty(message = "Empty 'username' field")
    private String username;
    @NotEmpty(message = "Empty 'password' field")
    private String password;
//    @NotEmpty(message = "Empty 'role' field") TODO: crate validator for Role enum
    private Role role;
}
