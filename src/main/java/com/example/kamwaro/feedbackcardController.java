package com.example.kamwaro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class feedbackcardController {
    @FXML
    private Label topicNameLabel;

    @FXML
    private Label commentLabel;

    private DashboardController con;

    public  void setFeedbackDetails(String topicName, String comment, DashboardController con) {
        topicNameLabel.setText(topicName);
        commentLabel.setText(comment);
        this.con = con;
    }

    @FXML
    private void handleDelete() {
        // Call a method in the main controller to delete the class from the database and refresh the VBox
        con.deleteFeedbackFromDatabase(topicNameLabel.getText());
    }
}
