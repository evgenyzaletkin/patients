package com.home.patients.app.service.matchers;

import com.home.patients.app.entities.Group;
import com.home.patients.app.entities.Patient;
import com.home.patients.app.entities.Status;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class SocialGroupFinder implements GroupFinder {
    @Override
    public Group getGroup() {
        return Group.SOCIAL;
    }

    @Override
    public boolean isPatientBelongToGroup(Patient patient) {
        return patient.getStatus() == Status.EMPLOYED || patient.isSmoker() || patient.isDrinker()
            || patient.isDrugUser();
    }

    @Override
    public List<Patient> findPatientsByGroup() {
        return null;
    }

    @Override
    public List<Patient> findPatientsByGroup(Pageable pageable) {
        return null;
    }
}
