package com.example.doctor_patient_suggestion.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.doctor_patient_suggestion.entity.Doctor;
import com.example.doctor_patient_suggestion.entity.Patient;
import com.example.doctor_patient_suggestion.entity.Speciality;
import com.example.doctor_patient_suggestion.entity.Symptom;
import com.example.doctor_patient_suggestion.repository.DoctorRepository;
import com.example.doctor_patient_suggestion.repository.PatientRepository;

@Service
public class SuggestionService {

    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;

    public SuggestionService(DoctorRepository doctorRepo, PatientRepository patientRepo) {
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
    }

    public List<Doctor> suggestDoctors(Long patientId) {
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        String patientCity = patient.getCity();
        Speciality speciality = mapSymptomToSpeciality(patient.getSymptom());

        // Edge case: unsupported city
        if (!List.of("Delhi", "Noida", "Faridabad").contains(patientCity)) {
            throw new ResponseStatusException(HttpStatus.OK, "We are still waiting to expand to your location");
        }

        List<Doctor> doctors = doctorRepo.findByCityAndSpeciality(patientCity, speciality);

        if (doctors.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.OK,
                    "There isn’t any doctor present at your location for your symptom");
        }

        return doctors;
    }

    private Speciality mapSymptomToSpeciality(Symptom symptom) {
        return switch (symptom) {
            case ARTHRITIS, BACK_PAIN, TISSUE_INJURIES -> Speciality.ORTHOPAEDIC;
            case DYSMENORRHEA -> Speciality.GYNECOLOGY;
            case SKIN_INFECTION, SKIN_BURN -> Speciality.DERMATOLOGY;
            case EAR_PAIN -> Speciality.ENT;
        };
    }
}