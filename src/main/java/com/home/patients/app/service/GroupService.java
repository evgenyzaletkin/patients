package com.home.patients.app.service;

import com.home.patients.app.entities.Group;
import com.home.patients.app.entities.Patient;
import com.home.patients.app.service.matchers.GroupFinder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupService {

    final Map<Group, GroupFinder> groupsToFinders;

    public GroupService(Map<Group, GroupFinder> groupsToFinders) {
        this.groupsToFinders = groupsToFinders;
    }

    public void assignGroupToPatient(Patient patient) {
        Set<Group> groups = groupsToFinders.values().stream()
            .filter(matcher -> matcher.isPatientBelongToGroup(patient))
            .map(GroupFinder::getGroup)
            .collect(Collectors.toSet());
        patient.setGroups(groups);

    }

    public List<Patient> findByGroups(Set<Group> groupSet) {
        return Collections.emptyList();
    }
}
