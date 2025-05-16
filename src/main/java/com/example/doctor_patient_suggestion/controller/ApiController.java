package com.example.doctor_patient_suggestion.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.doctor_patient_suggestion.entity.Doctor;
import com.example.doctor_patient_suggestion.entity.Patient;
import com.example.doctor_patient_suggestion.repository.DoctorRepository;
import com.example.doctor_patient_suggestion.repository.PatientRepository;
import com.example.doctor_patient_suggestion.service.SuggestionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Validated
public class ApiController {

    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;
    private final SuggestionService suggestionService;

    public ApiController(DoctorRepository doctorRepo, PatientRepository patientRepo,
            SuggestionService suggestionService) {
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
        this.suggestionService = suggestionService;
    }

    @PostMapping("/doctors")
    public Doctor addDoctor(@Valid @RequestBody Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    @PostMapping("/patients")
    public Patient addPatient(@Valid @RequestBody Patient patient) {
        return patientRepo.save(patient);
    }

    @GetMapping("/suggest-doctor/{patientId}")
    public ResponseEntity<?> suggestDoctors(@PathVariable Long patientId) {
        try {
            return ResponseEntity.ok(suggestionService.suggestDoctors(patientId));
        } catch (ResponseStatusException e) {
            return ResponseEntity.ok(Map.of("message", e.getReason()));
        }
    }
}