package com.example.kamwaro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class reviewscardController {
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label feedbackLabel;

    public void setFeedbackData(ReviewsModel review) {
        fullNameLabel.setText(review.getFullName());
        feedbackLabel.setText(review.getFeedback());
    }
}
