package com.home.patients.app.repositories;

import com.home.patients.app.entities.Disease;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseasesRepository extends JpaRepository<Disease, Long> {
}
