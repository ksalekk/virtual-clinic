package com.jsalek.pw.virtualclinic.domain.patient;

import com.jsalek.pw.virtualclinic.security.authorization.AuthGuard;
import com.jsalek.pw.virtualclinic.security.user.Principal;
import com.jsalek.pw.virtualclinic.security.user.Role;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PatientService(PatientRepository patientRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }


    public List<PatientShortDto> getAllPatients() {
        List<Patient> patients = (List<Patient>) this.patientRepository.findAll();
        return patients
                .stream()
                .map(pojo -> this.modelMapper.map(pojo, PatientShortDto.class))
                .toList();
    }


    public Optional<PatientShortDto> getPatientShortById(Long patientId) {

        Optional<Patient> patient = this.patientRepository.findById(patientId);
        return patient.map( pojo -> this.modelMapper.map(pojo, PatientShortDto.class) );
    }


    public Optional<PatientDetailsDto> getPatientDetailsById(Long patientId) {

        Optional<Patient> patient = this.patientRepository.findById(patientId);
        return patient.map( pojo -> this.modelMapper.map(pojo, PatientDetailsDto.class) );
    }


    public Optional<PatientDetailsDto> updatePatient(PatientDetailsDto updatedPatient, Long patientId) {

        return this.patientRepository.findById(patientId)
                .map( pojo -> {
                            if(updatedPatient.getFirstname() != null) {
                                pojo.setFirstname(updatedPatient.getFirstname());
                            }
                            if(updatedPatient.getLastname() != null) {
                                pojo.setLastname(updatedPatient.getLastname());
                            }
                            if(updatedPatient.getDateOfBirth() != null) {
                                pojo.setDateOfBirth(updatedPatient.getDateOfBirth());
                            }
                            if(updatedPatient.getPesel() != null) {
                                pojo.setPesel(updatedPatient.getPesel());
                            }
                            if(updatedPatient.getGender() != null) {
                                pojo.setGender(updatedPatient.getGender());
                            }
                            if(updatedPatient.getPhoneNumber() != null) {
                                pojo.setPhoneNumber(updatedPatient.getPhoneNumber());
                            }
                            if(updatedPatient.getAddress() != null) {
                                pojo.getAddress().setCountry(updatedPatient.getAddress().getCountry());
                                pojo.getAddress().setCity(updatedPatient.getAddress().getCity());
                                pojo.getAddress().setStreet(updatedPatient.getAddress().getStreet());
                                pojo.getAddress().setPostCode(updatedPatient.getAddress().getPostCode());
                            }
                            // horrible... TODO: change it

                            return this.modelMapper.map(this.patientRepository.save(pojo), PatientDetailsDto.class);
                        });

    }

    public Optional<Patient> getPatientEntity(Long patientId) {
        return this.patientRepository.findById(patientId);
    }

}
