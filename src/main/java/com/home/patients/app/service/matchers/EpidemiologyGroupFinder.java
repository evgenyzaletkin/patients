package com.home.patients.app.service.matchers;

import com.home.patients.app.entities.Group;
import com.home.patients.app.entities.Patient;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class EpidemiologyGroupFinder implements GroupFinder {

    private final int kkfYear;

    public EpidemiologyGroupFinder(int kkfYear) {
        this.kkfYear = kkfYear;
    }

    @Override
    public Group getGroup() {
        return Group.EPIDEMIOLOGY;
    }

    @Override
    public boolean isPatientBelongToGroup(Patient patient) {
        return patient.isTuberculosis() || kkfYear >= patient.getKkf();
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
