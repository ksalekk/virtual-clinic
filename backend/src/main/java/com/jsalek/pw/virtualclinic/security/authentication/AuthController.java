package com.jsalek.pw.virtualclinic.security.authentication;

import com.jsalek.pw.virtualclinic.security.user.Principal;
import com.jsalek.pw.virtualclinic.security.user.SignupDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Principal> registerUser(@Valid @RequestBody SignupDto signUpDto) {
        return this.authService.registerUser(signUpDto)
                .map( principal -> new ResponseEntity<>(principal, HttpStatus.CREATED) )
                .orElse(ResponseEntity.unprocessableEntity().build());
    }

    @GetMapping("/login")
    public ResponseEntity<Principal> authenticateWithAuthorizationField(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationField,
            HttpSession httpSession
    ) {
        return this.authService.authenticate(authorizationField, httpSession)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/logout")
    public ResponseEntity<Principal> logout(HttpSession httpSession) {
        return ResponseEntity.ok(this.authService.logout(httpSession));
    }

}
