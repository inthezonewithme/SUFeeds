package com.example.kamwaro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class topiccardController {
    @FXML
    private Label topicNameLabel;

    @FXML
    private Label weekNumberLabel;

    private DashboardController con;

    public  void setTopicDetails(String topicName, String weekNumber, DashboardController con) {
        topicNameLabel.setText(topicName);
        weekNumberLabel.setText("Week "+weekNumber);
        this.con = con;
    }
    @FXML
    private void handleDelete() {
        // Call a method in the main controller to delete the class from the database and refresh the VBox
        con.deleteTopicFromDatabase(topicNameLabel.getText());
    }

}
