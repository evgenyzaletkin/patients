package com.home.patients;

import com.home.patients.app.ContextHelper;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

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
        Locale locale = new Locale("ru", "RU");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles/sample", locale);
        Parent root = FXMLLoader.load(url, bundle);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

}
