package com.home.patients.app.service.matchers;

import com.home.patients.app.entities.Group;
import com.home.patients.app.entities.Patient;

import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DiseaseGroupFinder implements GroupFinder {

    private final Set<Long> diseaseIds;
    private final Group group;

    public DiseaseGroupFinder(Set<Long> diseaseIds, Group group) {
        this.diseaseIds = diseaseIds;
        this.group = group;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public boolean isPatientBelongToGroup(Patient patient) {
        return patient.getDiseases().stream()
            .anyMatch(d -> diseaseIds.contains(d.getId()));
    }

    @Override
    public List<Patient> findPatientsByGroup() {
        return Collections.emptyList();
    }

    @Override
    public List<Patient> findPatientsByGroup(Pageable pageable) {
        return Collections.emptyList();
    }
}
