package com.home.patients.app.repositories;

import com.home.patients.app.common.TitleMapper;
import com.home.patients.app.entities.Disease;

import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DiseasesRepositoryImpl implements DiseasesRepositoryExtended {

    private final EntityManager entityManager;
    private final TitleMapper titleMapper;
    private final TransactionTemplate transactionTemplate;

    private Disease hiv;
    private Disease sd;
    private Disease cobr;
    private Disease gi;
    private Disease tb;

    public DiseasesRepositoryImpl(EntityManager entityManager,
                                  TitleMapper titleMapper, TransactionTemplate transactionTemplate) {
        this.entityManager = entityManager;
        this.titleMapper = titleMapper;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Disease getHiv() {
        return hiv;
    }

    @Override
    public Disease getSd() {
        return sd;
    }

    @Override
    public Disease getCobr() {
        return cobr;
    }

    @Override
    public Disease getGi() {
        return gi;
    }

    @Override
    public Disease getTb() {
        return tb;
    }

    @PostConstruct
    public void initDiseases() {
        transactionTemplate.execute(transactionStatus -> {
            Disease hiv = new Disease();
            hiv.setName(titleMapper.getHivName());
            DiseasesRepositoryImpl.this.hiv = hiv;
            entityManager.persist(hiv);

            Disease sd = new Disease();
            sd.setName(titleMapper.getSdName());
            DiseasesRepositoryImpl.this.sd = sd;
            entityManager.persist(sd);

            Disease copd = new Disease();
            copd.setName(titleMapper.getCobrName());
            DiseasesRepositoryImpl.this.cobr = copd;
            entityManager.persist(copd);

            Disease gi = new Disease();
            gi.setName(titleMapper.getGiName());
            DiseasesRepositoryImpl.this.gi = gi;
            entityManager.persist(gi);

            Disease tb = new Disease();
            tb.setName(titleMapper.getTbColumn());
            DiseasesRepositoryImpl.this.tb = tb;
            entityManager.persist(tb);

            log.info("Default diseases have been saved");
            return null;
        });

    }


}
