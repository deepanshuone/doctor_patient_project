package com.example.doctor_patient_suggestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.doctor_patient_suggestion.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {}