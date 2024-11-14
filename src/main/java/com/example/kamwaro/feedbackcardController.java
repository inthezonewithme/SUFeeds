package com.example.kamwaro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class feedbackcardController {
    @FXML
    private Label topicNameLabel;

    @FXML
    private Label commentLabel;

    public  void setFeedbackDetails(String topicName, String comment) {
        topicNameLabel.setText(topicName +"- ");
        commentLabel.setText(comment);
    }

}
