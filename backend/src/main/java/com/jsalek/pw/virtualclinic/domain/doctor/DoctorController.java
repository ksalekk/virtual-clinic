package com.jsalek.pw.virtualclinic.domain.doctor;

import com.jsalek.pw.virtualclinic.global.ResourceNotFoundException;
import com.jsalek.pw.virtualclinic.security.authorization.AuthGuard;
import com.jsalek.pw.virtualclinic.security.user.Principal;
import com.jsalek.pw.virtualclinic.security.user.Role;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    private final DoctorService doctorService;
    private final AuthGuard authGuard;

    @Autowired
    public DoctorController(DoctorService doctorService, AuthGuard authGuard) {
        this.doctorService = doctorService;
        this.authGuard = authGuard;
    }




    @GetMapping
    public List<DoctorDto> getDoctors() {
        // patient => cannot
        // doctor => cannot
        // staff => all doctors in clinic

        // GET doctors => restricted to STAFF role (filtering by role is enough)

        return this.doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctor(@PathVariable Long id, HttpSession httpSession) {
        // patient => cannot
        // doctor => own details
        // staff => can

        // GET doctors/id => filter pass only STAFF and DOCTOR; if DOCTOR then followed condition must be true: principal.id == id
        Principal principal = (Principal) httpSession.getAttribute("principal");
        if(this.authGuard.checkIdorOccurence(principal, Role.DOCTOR, id)) {
            return ResponseEntity.notFound().build();
        } else {
            return this.doctorService.getDoctorDtoById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@Valid @RequestBody DoctorDto doctor, @PathVariable Long id, HttpSession httpSession) {
        // patient => cannot
        // doctor => only own
        // staff => cannot

        // PUT doctors/id => filter pass only DOCTOR; additional condition must be true: principal.id == id
        Principal principal = (Principal) httpSession.getAttribute("principal");
        if(this.authGuard.checkIdorOccurence(principal, Role.DOCTOR, id)) {
            return ResponseEntity.notFound().build();
        } else {
            return this.doctorService.updateDoctor(doctor, id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }

}
