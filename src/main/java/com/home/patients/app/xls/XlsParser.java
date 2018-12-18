package com.home.patients.app.xls;

import static java.util.stream.StreamSupport.stream;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.home.patients.app.common.Translations;
import com.home.patients.app.common.Translator;
import com.home.patients.app.entities.Disease;
import com.home.patients.app.entities.Group;
import com.home.patients.app.entities.Patient;
import com.home.patients.app.entities.Sex;
import com.home.patients.app.entities.Status;
import com.home.patients.app.repositories.DiseasesRepository;
import com.home.patients.app.repositories.PatientsRepository;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class XlsParser {


    private final PatientsRepository patientsRepository;
    private final Translator translator;
    private final DiseasesRepository diseasesRepository;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public XlsParser(PatientsRepository patientsRepository, Translator translator,
                     DiseasesRepository diseasesRepository) {
        this.patientsRepository = patientsRepository;
        this.translator = translator;
        this.diseasesRepository = diseasesRepository;
    }

    @Transactional
    public List<Patient> parseAndSavePatients(File file) throws Exception {
        Workbook workbook = WorkbookFactory.create(file);
        Preconditions.checkState(workbook.getNumberOfSheets() == 1,
            "We expect only one sheet in xls file");
        Sheet sheet = workbook.getSheetAt(0);
        Map<String, Integer> mapping = createColumnMapping(sheet.getRow(0));
        List<Patient> patients = stream(Spliterators.spliteratorUnknownSize(sheet.rowIterator(),
            Spliterator.ORDERED), false)
            .skip(1)
            .map(row -> new RowWithMapping(row, mapping))
            .map(this::parsePatient)
            .collect(Collectors.toList());
        patientsRepository.saveAll(patients);
        return patients;
    }

    private Map<String, Integer> createColumnMapping(Row row) {
        int i = 0;
        Map<String, Integer> mapping = new HashMap<>();
        for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext(); i++) {
            Cell cell = cellIterator.next();
            String columnName = cell.getStringCellValue();
            mapping.put(columnName.toUpperCase(), i);
        }
        return mapping;
    }

    private Patient parsePatient(RowWithMapping rowWithMapping) {
        try {
            Patient patient = new Patient();
            patient.setName(rowWithMapping.getStringValue(translator.getNameColumn()));
            patient.setBirthDate(rowWithMapping.getDateValue(translator.getBirthdateColumn()));
            patient.setSex(rowWithMapping.getSex(translator.getSexColumn()));
            patient.setAddress(rowWithMapping.getStringValue(translator.getAddressColumn()));
            patient.setStatus(rowWithMapping.getStatus(translator.getStatusColumn()));
            patient.setDrinker(rowWithMapping.getBoolValue(translator.getDrinkerColumn()));
            patient.setDrugUser(rowWithMapping.getBoolValue(translator.getDrugsColumn()));
            patient.setSmoker(rowWithMapping.getBoolValue(translator.getSmokingColumn()));
            patient.setFsin(rowWithMapping.getBoolValue(translator.getFsinColumn()));
            patient.setDiseases(parseDiseases(rowWithMapping));
            patient.setGroups(EnumSet.allOf(Group.class));
            return patient;
        } catch (ParseException e) {
            throw new IllegalStateException(String.format("Can't parse row %s",
                rowWithMapping.row.getRowNum()), e);
        }
    }

    private List<Disease> parseDiseases(RowWithMapping row) {
        List<Disease> diseases = Lists.newArrayList();
        if (row.getBoolValue(translator.getHivName())) {
            diseases.add(diseasesRepository.getHiv());
        }
        if (row.getBoolValue(translator.getSdName())) {
            diseases.add(diseasesRepository.getSd());
        }
        if (row.getBoolValue(translator.getCobrName())) {
            diseases.add(diseasesRepository.getCobr());
        }
        if (row.getBoolValue(translator.getGiName())) {
            diseases.add(diseasesRepository.getGi());
        }
        if (row.getBoolValue(translator.getTbColumn())) {
            diseases.add(diseasesRepository.getTb());
        }
        return diseases;
    }



    private class RowWithMapping {
        private final Row row;
        private final Map<String, Integer> mapping;

        private RowWithMapping(Row row, Map<String, Integer> mapping) {
            this.row = row;
            this.mapping = mapping;
        }

        private String getStringValue(String columnName) {
            Integer index = mapping.get(columnName.toUpperCase());
            Cell textCell = row.getCell(index);
            return textCell.getStringCellValue();
        }

        private Date getDateValue(String columnName) throws ParseException {
            Integer index = mapping.get(columnName);
            Cell dateCell = row.getCell(index);
            return dateCell.getDateCellValue();
        }

        private Sex getSex(String columnName) {
            String sexString = getStringValue(columnName);
            return translator.getMaleSex().equalsIgnoreCase(sexString) ? Sex.MALE :
                translator.getFemaleSex().equalsIgnoreCase(sexString) ? Sex.FEMALE : null;
        }

        private Status getStatus(String columnName) {
            String statusString = getStringValue(columnName);
            if (translator.getEmployed().equalsIgnoreCase(statusString)) {
                return Status.EMPLOYED;
            } else if (translator.getRetiree().equalsIgnoreCase(statusString)) {
                return Status.RETIREE;
            } else if (translator.getUnemployed().equalsIgnoreCase(statusString)) {
                return Status.NON_EMPLOYED;
            } else {
                return null;
            }
        }

        private boolean getBoolValue(String columnName) {
            String boolString = getStringValue(columnName);
            return translator.getYesValue().equalsIgnoreCase(boolString);
        }


    }
}
