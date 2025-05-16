package com.example.doctor_patient_suggestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.doctor_patient_suggestion.entity.Doctor;
import com.example.doctor_patient_suggestion.entity.Speciality;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByCityAndSpeciality(String city, Speciality speciality);
}