package com.home.patients.app.configs;

import com.google.common.collect.ImmutableSet;
import com.home.patients.app.entities.Group;
import com.home.patients.app.repositories.DiseasesRepository;
import com.home.patients.app.service.GroupService;
import com.home.patients.app.service.matchers.DiseaseGroupFinder;
import com.home.patients.app.service.matchers.EpidemiologyGroupFinder;
import com.home.patients.app.service.matchers.GroupFinder;
import com.home.patients.app.service.matchers.SocialGroupFinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class GroupsConfig {

    @Autowired
    DiseasesRepository diseasesRepository;

    @Bean
    public DiseaseGroupFinder hivGroupFinder() {
        return new DiseaseGroupFinder(Collections.singleton(diseasesRepository.getHiv().getId()), Group.HIV);
    }

    @Bean
    public DiseaseGroupFinder medicalGroupFinder() {
        Set<Long> ids = ImmutableSet.of(
            diseasesRepository.getSd().getId(),
            diseasesRepository.getCobr().getId(),
            diseasesRepository.getGi().getId()
        );
        return new DiseaseGroupFinder(ids, Group.MD);
    }

    @Bean
    public SocialGroupFinder socialGroupFinder() {
        return new SocialGroupFinder();
    }

    @Bean
    public EpidemiologyGroupFinder epidemiologyGroupFinder() {
        return new EpidemiologyGroupFinder(2016);
    }

    @Bean
    public GroupService groupService(List<GroupFinder> finders) {
        Map<Group, GroupFinder> findersByGroup = finders.stream()
            .collect(Collectors.toMap(GroupFinder::getGroup, Function.identity()));
        return new GroupService(findersByGroup);
    }

}
