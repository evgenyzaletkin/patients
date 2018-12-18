package com.home.patients.controllers;

import com.home.patients.app.ContextHelper;
import com.home.patients.app.entities.LocalizedPatientDto;
import com.home.patients.app.entities.Patient;
import com.home.patients.app.service.PatientService;
import com.home.patients.app.xls.XlsParser;
import com.sun.javafx.collections.ObservableListWrapper;

import java.io.File;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PatientsController {

    @FXML
    private TableView<LocalizedPatientDto> patientsTable;

    @FXML
    private Stage primaryStage;

    private XlsParser parser = ContextHelper.getBean(XlsParser.class);

    private PatientService patientService = ContextHelper.getBean(PatientService.class);

    public void chooseFileDialog(ActionEvent actionEvent) throws Exception {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XLS files (*.xls, *.xlsx)",
            "*.xls", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(primaryStage);
        log.info("Import patients from file {}", file);
        List<Patient> patients = parser.parseAndSavePatients(file);
        List<LocalizedPatientDto> localizedPatientDtos = patientService.transformToDtos(patients);
        patientsTable.setItems(new ObservableListWrapper<>(localizedPatientDtos));
    }
}
