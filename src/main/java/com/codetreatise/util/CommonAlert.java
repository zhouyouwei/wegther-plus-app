package com.codetreatise.util;

import javafx.scene.control.Alert;

public class CommonAlert {

    public static void alert(String title,String headerText, String message){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
       // alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
