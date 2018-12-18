package com.home.patients.app.common;

import com.home.patients.app.entities.Group;
import com.home.patients.app.entities.Sex;

public interface Translator {


    String getNameColumn();
    String getBirthdateColumn();
    String getSexColumn();
    String getAddressColumn();
    String getHivName();
    String getSdName();
    String getCobrName();
    String getGiName();
    String getStatusColumn();
    String getSmokingColumn();
    String getDrinkerColumn();
    String getDrugsColumn();
    String getFsinColumn();
    String getTbColumn();
    String getTbName();
    String getKkfColumn();
    String getEmployed();
    String getUnemployed();
    String getRetiree();
    String getFemaleSex();
    String getMaleSex();
    String getYesValue();

    String getMedicalGroup();

    String getSocialGroup();

    String getEpidemiologyGroup();


    default String getTranslatedSex(Sex sex) {
        switch (sex) {
            case FEMALE:
                return getFemaleSex();
            case MALE:
                return getMaleSex();
            default:
                return null;
        }
    }

    default String getTranslatedGroup(Group group) {
        switch (group) {
            case HIV:
                return getHivName();
            case SOCIAL:
                return getSocialGroup();
            case MD:
                return getMedicalGroup();
            case EPIDEMIOLOGY:
                return getEpidemiologyGroup();
            default:
                throw new IllegalArgumentException("Unsupported Group" + group);
        }
    }
}
