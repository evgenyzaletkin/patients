package com.home.patients.app.service.matchers;

import com.home.patients.app.entities.Group;
import com.home.patients.app.entities.Patient;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupFinder {
    Group getGroup();
    boolean isPatientBelongToGroup(Patient patient);
    List<Patient> findPatientsByGroup();
    List<Patient> findPatientsByGroup(Pageable pageable);
}
