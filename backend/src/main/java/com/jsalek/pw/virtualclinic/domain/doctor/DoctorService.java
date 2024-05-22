package com.jsalek.pw.virtualclinic.domain.doctor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }




    public List<DoctorDto> getAllDoctors() {
        List<Doctor> doctors = (List<Doctor>) this.doctorRepository.findAll();
        return doctors
                .stream()
                .map(pojo -> this.modelMapper.map(pojo, DoctorDto.class))
                .toList();
    }


    public Optional<DoctorDto> getDoctorDtoById(Long id) {
        return doctorRepository.findById(id)
                .map(pojo -> this.modelMapper.map(pojo, DoctorDto.class));
    }


    public Optional<DoctorDto> updateDoctor(DoctorDto updatedDoctor, Long doctorId) {
        return this.doctorRepository.findById(doctorId)
                .map( pojo -> {
                    if(updatedDoctor.getFirstname() != null) {
                        pojo.setFirstname(updatedDoctor.getFirstname());
                    }
                    if(updatedDoctor.getLastname() != null) {
                        pojo.setLastname(updatedDoctor.getLastname());
                    }
                    if(updatedDoctor.getPhoneNumber() != null) {
                        pojo.setPhoneNumber(updatedDoctor.getPhoneNumber());
                    }
//                    if(updatedDoctor.getLicenseNumber() != null) {
//                        pojo.setLicenseNumber(updatedDoctor.getLicenseNumber());
//                    }
//                    if(updatedDoctor.getSpeciality() != null) {
//                        pojo.setSpeciality(updatedDoctor.getSpeciality());
//                    }
                    // horrible... TODO: change it
                    return this.modelMapper.map(this.doctorRepository.save(pojo), DoctorDto.class);
                });

    }


    public Optional<Doctor> getDoctorEntity(Long doctorId) {
        return this.doctorRepository.findById(doctorId);
    }

}
