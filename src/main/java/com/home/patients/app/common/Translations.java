package com.home.patients.app.common;

import java.util.ResourceBundle;

public class Translations {

    private final static ResourceBundle BUNDLE = ResourceBundle.getBundle("sample");

    public static String getNameColumn() {
        return BUNDLE.getString("NAME_COLUMN");
    }

}
