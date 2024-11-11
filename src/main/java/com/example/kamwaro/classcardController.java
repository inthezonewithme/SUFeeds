package com.example.kamwaro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class classcardController {

    @FXML
    public Label classNameLabel;

    @FXML
    public  Label classCodeLabel;

    public  void setClassDetails(String className, String classCode) {
        classNameLabel.setText(className);
        classCodeLabel.setText(classCode);
    }
}
