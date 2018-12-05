package com.home.patients.app.configs;

import com.home.patients.app.entities.Disease;
import com.home.patients.app.repositories.DiseasesRepository;

import org.springframework.stereotype.Service;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DbInitializer {

    private final DiseasesRepository diseasesRepository;

    public DbInitializer(DiseasesRepository diseasesRepository) {
        this.diseasesRepository = diseasesRepository;
    }

    @PostConstruct
    public void initDiseases() {
        Disease hiv = new Disease();
        hiv.setName("ВИЧ");

        Disease sd = new Disease();
        sd.setName("СД");

        Disease copd = new Disease();
        copd.setName("ХНЗЛ");

        Disease gi = new Disease();
        gi.setName("Заболевания ЖКТ");

        diseasesRepository.saveAll(Arrays.asList(hiv, sd, copd, gi));
        log.info("Default diseases have been saved");
    }
}
