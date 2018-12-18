package com.home.patients.app.common;

import org.springframework.stereotype.Component;

@Component
public class RussianTranslator implements Translator {

    private static final String NAME_COLUMN = "ФИО";
    private static final String BIRTHDATE_COLUMN = "ДАТА РОЖДЕНИЯ";
    private static final String SEX_COLUMN = "ПОЛ";
    private static final String ADDRESS_COLUMN = "АДРЕС";
    private static final String HIV_COLUMN = "ВИЧ";
    private static final String SD_COLUMN = "СД";
    private static final String COBR_COLUMN = "ХНЗЛ";
    private static final String GI_COLUMN = "ЗАБОЛЕВАНИЯ ЖКТ";
    private static final String STATUS_COLUMN = "ЗАНЯТОСТЬ";
    private static final String SMOKING_COLUMN = "КУРЕНИЕ";
    private static final String DRINKING_COLUMN = "АЛКОГОЛЬ";
    private static final String DRUGS_COLUMN = "НАРКОТИКИ";
    private static final String FSIN_COLUMN = "ФСИН";
    private static final String TB_COLUMN = "ТБ В АНАМ";
    private static final String TB = "ТБ";
    private static final String KKF_COLUMN = "ПРЕД.ККФ";
    private static final String EMPLOYED = "РАБОТАЮЩИЙ";
    private static final String RETIREE = "ПЕНСИОНЕР";
    private static final String UNEMPLOYED = "НЕРАБОТАЮЩИЙ";
    private static final String M_SEX = "М";
    private static final String F_SEX = "Ж";
    private static final String YES_VALUE = "ДА";

    @Override
    public String getNameColumn() {
        return NAME_COLUMN;
    }

    @Override
    public String getBirthdateColumn() {
        return BIRTHDATE_COLUMN;
    }

    @Override
    public String getSexColumn() {
        return SEX_COLUMN;
    }

    @Override
    public String getAddressColumn() {
        return ADDRESS_COLUMN;
    }

    @Override
    public String getHivName() {
        return HIV_COLUMN;
    }

    @Override
    public String getSdName() {
        return SD_COLUMN;
    }

    @Override
    public String getCobrName() {
        return COBR_COLUMN;
    }

    @Override
    public String getGiName() {
        return GI_COLUMN;
    }

    @Override
    public String getStatusColumn() {
        return STATUS_COLUMN;
    }

    @Override
    public String getSmokingColumn() {
        return SMOKING_COLUMN;
    }

    @Override
    public String getDrinkerColumn() {
        return DRINKING_COLUMN;
    }

    @Override
    public String getDrugsColumn() {
        return DRUGS_COLUMN;
    }

    @Override
    public String getFsinColumn() {
        return FSIN_COLUMN;
    }

    @Override
    public String getTbColumn() {
        return TB_COLUMN;
    }

    @Override
    public String getTbName() {
        return TB;
    }

    @Override
    public String getKkfColumn() {
        return KKF_COLUMN;
    }

    @Override
    public String getEmployed() {
        return EMPLOYED;
    }

    @Override
    public String getUnemployed() {
        return UNEMPLOYED;
    }

    @Override
    public String getRetiree() {
        return RETIREE;
    }

    @Override
    public String getFemaleSex() {
        return F_SEX;
    }

    @Override
    public String getMaleSex() {
        return M_SEX;
    }

    @Override
    public String getYesValue() {
        return YES_VALUE;
    }

    @Override
    public String getMedicalGroup() {
        return "МЕДИЦИНСКИЕ";
    }

    @Override
    public String getSocialGroup() {
        return "СОЦИАЛЬНЫЕ";
    }

    @Override
    public String getEpidemiologyGroup() {
        return "ЭПИДЕМИОЛОГИЧЕСКИЕ";
    }
}
