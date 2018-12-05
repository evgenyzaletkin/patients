package com.home.patients.app.repositories;

import com.home.patients.app.entities.Patient;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientsRepository extends JpaRepository<Patient, Long> {
}
