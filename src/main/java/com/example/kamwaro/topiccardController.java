package com.example.kamwaro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class topiccardController {
    @FXML
    private Label topicNameLabel;

    @FXML
    private Label weekNumberLabel;

    public  void setTopicDetails(String topicName, String weekNumber) {
        topicNameLabel.setText(topicName);
        weekNumberLabel.setText("Week "+weekNumber);
    }

}
