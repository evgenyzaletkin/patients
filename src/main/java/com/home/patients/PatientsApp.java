package com.home.patients;

import com.home.patients.app.ContextHelper;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PatientsApp extends Application {
//    public static void main(String[] args) {
//        launch(args);
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ContextHelper.initContext();
        URL url = getClass().getClassLoader().getResource("sample.fxml");
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

}
