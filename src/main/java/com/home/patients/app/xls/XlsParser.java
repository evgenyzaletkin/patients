package com.home.patients.app.xls;

import static java.util.stream.StreamSupport.stream;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.home.patients.app.common.TitleMapper;
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
    private final TitleMapper titleMapper;
    private final DiseasesRepository diseasesRepository;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public XlsParser(PatientsRepository patientsRepository, TitleMapper titleMapper,
                     DiseasesRepository diseasesRepository) {
        this.patientsRepository = patientsRepository;
        this.titleMapper = titleMapper;
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
            patient.setName(rowWithMapping.getStringValue(titleMapper.getNameColumn()));
            patient.setBirthDate(rowWithMapping.getDateValue(titleMapper.getBirthdateColumn()));
            patient.setSex(rowWithMapping.getSex(titleMapper.getSexColumn()));
            patient.setAddress(rowWithMapping.getStringValue(titleMapper.getAddressColumn()));
            patient.setStatus(rowWithMapping.getStatus(titleMapper.getStatusColumn()));
            patient.setDrinker(rowWithMapping.getBoolValue(titleMapper.getDrinkerColumn()));
            patient.setDrugUser(rowWithMapping.getBoolValue(titleMapper.getDrugsColumn()));
            patient.setSmoker(rowWithMapping.getBoolValue(titleMapper.getSmokingColumn()));
            patient.setFsin(rowWithMapping.getBoolValue(titleMapper.getFsinColumn()));
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
        if (row.getBoolValue(titleMapper.getHivName())) {
            diseases.add(diseasesRepository.getHiv());
        }
        if (row.getBoolValue(titleMapper.getSdName())) {
            diseases.add(diseasesRepository.getSd());
        }
        if (row.getBoolValue(titleMapper.getCobrName())) {
            diseases.add(diseasesRepository.getCobr());
        }
        if (row.getBoolValue(titleMapper.getGiName())) {
            diseases.add(diseasesRepository.getGi());
        }
        if (row.getBoolValue(titleMapper.getTbColumn())) {
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
            return titleMapper.getMaleSex().equalsIgnoreCase(sexString) ? Sex.MALE :
                titleMapper.getFemaleSex().equalsIgnoreCase(sexString) ? Sex.FEMALE : null;
        }

        private Status getStatus(String columnName) {
            String statusString = getStringValue(columnName);
            if (titleMapper.getEmployed().equalsIgnoreCase(statusString)) {
                return Status.EMPLOYED;
            } else if (titleMapper.getRetiree().equalsIgnoreCase(statusString)) {
                return Status.RETIREE;
            } else if (titleMapper.getUnemployed().equalsIgnoreCase(statusString)) {
                return Status.NON_EMPLOYED;
            } else {
                return null;
            }
        }

        private boolean getBoolValue(String columnName) {
            String boolString = getStringValue(columnName);
            return titleMapper.getYesValue().equalsIgnoreCase(boolString);
        }


    }
}
