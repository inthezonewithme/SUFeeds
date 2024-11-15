package com.example.kamwaro;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class classcardController {

    @FXML
    public Label classNameLabel;

    @FXML
    public  Label classCodeLabel;

    @FXML
    private Button deleteClassBtn;



    private DashboardController con;
    public  void setClassDetails(String className, String classCode, DashboardController con) {
        classNameLabel.setText(className);
        classCodeLabel.setText(classCode);
        this.con = con;
    }
    @FXML
    private void handleDelete() {
        // Call a method in the main controller to delete the class from the database and refresh the VBox
        con.deleteClassFromDatabase(classCodeLabel.getText());
    }
}
