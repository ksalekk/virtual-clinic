package com.jsalek.pw.virtualclinic.security.authentication;

import com.jsalek.pw.virtualclinic.domain.doctor.Doctor;
import com.jsalek.pw.virtualclinic.domain.patient.Patient;
import com.jsalek.pw.virtualclinic.domain.staff.Staff;
import com.jsalek.pw.virtualclinic.security.user.*;
import com.jsalek.pw.virtualclinic.security.utils.NoSuchRoleException;
import com.jsalek.pw.virtualclinic.security.utils.PasswordEncoder;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepo;

    @Autowired
    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<Principal> registerUser(SignUpDto signUpDto) {

        if(isUsernameExists(signUpDto.getUsername())) {
            return Optional.empty();
        }

        return this.getUserFromSignUpDto(signUpDto).map(pojo -> {
            pojo.setPassword(PasswordEncoder.getSHA256(signUpDto.getPassword()));
            User createdUser = this.userRepo.save(pojo);
            return new Principal(createdUser.getId(), createdUser.getRole());
        });
    }


    public Optional<Principal> authenticate(String authorizationField, HttpSession httpSession) {
        return basicAuthentication(authorizationField).map(principal -> {
            httpSession.setAttribute("principal", principal);
            return principal;
        });
    }


    public Principal logout(HttpSession httpSession) {
        if(httpSession != null) {
            Principal principal = (Principal) httpSession.getAttribute("principal");
            httpSession.invalidate();
            return principal;
        }
        return null;
    }


    private boolean isUsernameExists(String username) {
        return this.userRepo.findByUsername(username).isPresent();
    }


    private Optional<User> getUserFromSignUpDto(SignUpDto signUpDto) {
        if(signUpDto.getRole() == null) {
            return Optional.empty();
        }

        Optional<User> user = Optional.empty();
        switch(signUpDto.getRole()) {
            case Role.PATIENT -> user = Optional.of(new Patient());
            case Role.DOCTOR -> user = Optional.of(new Doctor());
            case Role.STAFF -> user = Optional.of(new Staff());
        }

        return user.map(pojo -> {
            pojo.setRole(signUpDto.getRole());
            pojo.setUsername(signUpDto.getUsername());
            return pojo;
        });
    }


    private Optional<Principal> basicAuthentication(String authorizationField) {
        String base64Credentials = authorizationField.split(" ")[1];
        byte[] credentialsBytes = Base64.getDecoder().decode(base64Credentials.getBytes());
        String[] decodedCredentials = new String(credentialsBytes).split(":");

        if(decodedCredentials.length != 2) {
            return Optional.empty();
        }
        String login = decodedCredentials[0];
        String password = decodedCredentials[1];

        Optional<User> user = this.userRepo.findByUsername(login);
        if(user.isPresent() && user.get().getPassword().equals(PasswordEncoder.getSHA256(password))) {
            return Optional.of(new Principal(
                    user.get().getId(),
                    user.get().getRole()
            ));
        }
        return Optional.empty();
    }

}
