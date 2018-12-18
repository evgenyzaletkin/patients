package com.home.patients.app.service;

import com.home.patients.app.common.Translator;
import com.home.patients.app.entities.Group;
import com.home.patients.app.entities.LocalizedPatientDto;
import com.home.patients.app.entities.Patient;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final Translator translator;
    private final GroupService groupService;

    public PatientService(Translator translator, GroupService groupService) {
        this.translator = translator;
        this.groupService = groupService;
    }

    public List<LocalizedPatientDto> transformToDtos(List<Patient> patients) {
        return patients.stream()
            .map(this::toPatientDto)
            .collect(Collectors.toList());
    }

    private LocalizedPatientDto toPatientDto(Patient patient) {
        LocalizedPatientDto dto = new LocalizedPatientDto();
        dto.setName(patient.getName());
        dto.setAddress(patient.getAddress());
        dto.setBirthdate(patient.getBirthDate());
        Set<Group> groups = patient.getGroups();
        if (groups == null) {
            groupService.assignGroupToPatient(patient);
            groups = patient.getGroups();
        }
        Set<String> translatedGroups = groups.stream().map(translator::getTranslatedGroup)
            .collect(Collectors.toSet());
        dto.setGroups(translatedGroups);
        dto.setSex(translator.getTranslatedSex(patient.getSex()));
        return dto;
    }
}
