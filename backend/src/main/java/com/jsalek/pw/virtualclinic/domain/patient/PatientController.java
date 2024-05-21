package com.jsalek.pw.virtualclinic.domain.patient;

import com.jsalek.pw.virtualclinic.security.authorization.AuthGuard;
import com.jsalek.pw.virtualclinic.security.user.Principal;
import com.jsalek.pw.virtualclinic.security.user.Role;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientService patientService;
    private final AuthGuard authGuard;

    @Autowired
    public PatientController(PatientService patientService, AuthGuard authGuard) {
        this.patientService = patientService;
        this.authGuard = authGuard;
    }



    @GetMapping
    public List<PatientShortDto> getPatients() {
        // patient => cannot
        // doctor => cannot
        // staff => all patients in clinic

        // GET patients => restricted to STAFF role (filtering by role is enough)
        return this.patientService.getAllPatients();
    }


    @GetMapping("/{id}")
    public ResponseEntity<PatientShortDto> getPatientShort(@PathVariable Long id, HttpSession httpSession) {
        // patient => only own
        // doctor => cannot
        // staff => all patients in clinic

        // GET patients/id => filter pass only STAFF and PATIENT; if PATIENT then followed condition must be true: principal.id == id
        Principal principal = (Principal) httpSession.getAttribute("principal");
        if(this.authGuard.checkIdorOccurence(principal, Role.PATIENT, id)) {
            return ResponseEntity.notFound().build();
        } else {
            return this.patientService.getPatientShortById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }


    @GetMapping("/{id}/details")
    public ResponseEntity<PatientDetailsDto> getPatientDetails(@PathVariable Long id, HttpSession httpSession) {
        // patient => only own
        // doctor => cannot
        // staff => cannot

        // GET patients/id/details => filter pass only PATIENT; additional condition must be true: principal.id == id
        Principal principal = (Principal) httpSession.getAttribute("principal");
        if(this.authGuard.checkIdorOccurence(principal, Role.PATIENT, id)) {
            return ResponseEntity.notFound().build();
        } else {
            return this.patientService.getPatientDetailsById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<PatientDetailsDto> updatePatient(@Valid @RequestBody PatientDetailsDto patient, @PathVariable Long id, HttpSession httpSession) {
        // patient => only own
        // doctor => cannot
        // staff => cannot

        // PUT patients/id => filter pass only PATIENT; additional condition must be true: principal.id == id
        Principal principal = (Principal) httpSession.getAttribute("principal");
        if(this.authGuard.checkIdorOccurence(principal, Role.PATIENT, id)) {
            return ResponseEntity.notFound().build();
        } else {
            return this.patientService.updatePatient(patient, id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }
}
