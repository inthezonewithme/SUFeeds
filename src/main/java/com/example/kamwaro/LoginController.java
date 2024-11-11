package com.example.kamwaro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML
    private TextField FName;
    @FXML
    private TextField LName;
    @FXML
    private TextField ID;
    @FXML
    private PasswordField Pass;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button SignUpButton;

    @FXML
    private Button CreateButton;

    @FXML
    private Button LoginButton;

    @FXML
    private void SignUpClicked(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new scene with the loaded FXML
            Scene scene = new Scene(root);

            // Set up a new stage (window) with the new scene
            Stage stage = new Stage();
            stage.setTitle("Sign Up Page");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin() {
        try{
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate username and password
        if (username.isEmpty() || password.isEmpty()) {
            showError("Both fields must be filled.");
            return;
        }

        // Check the credentials with the database
        if (authenticate(username, password)) {


            // Login successful, proceed to the next screen
            showInfo("Login successful!", "Welcome! "+username);

            DashboardController.studentId = username;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            // Get the controller and set the student ID


            // Load the next scene or window
            closeLoginWindow();
            openDashboardWindow();

        } else {
            // Login failed, show an error message
            showError("Invalid username or password.");
        }
    }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean authenticate(String username, String password) {
        String query = "SELECT * FROM tbl_users WHERE Student_Number = ? AND Password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Returns true if a record is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database error: " + e.getMessage());
            return false;
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void closeLoginWindow() {
        // Get the current stage (window) and close it
        Stage stage = (Stage) LoginButton.getScene().getWindow();
        stage.close();
    }

    private void openDashboardWindow() {
        try {
            // Load the dashboard.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            // Create a new scene for the dashboard
            Scene dashboardScene = new Scene(root);

            // Create a new stage (window) for the dashboard
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Dashboard");  // Set the title for the new window
            dashboardStage.setScene(dashboardScene);

            // Show the dashboard window
            dashboardStage.show();

            // Close the current login window
            closeLoginWindow();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading dashboard.");
        }
    }



    @FXML
    private void handleSignUp() {
        String fname = FName.getText();
        String lname = LName.getText();
        String stdno = ID.getText();
        String password = Pass.getText();

        if (fname.isEmpty() || lname.isEmpty() ||stdno.isEmpty()|| password.isEmpty()) {
            showAlert("Error", "All fields are required");
            return;
        }

        // Database insertion


        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO tbl_users (Student_Number, First_Name, Last_Name, Password ) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, stdno);
                statement.setString(2, fname);
                statement.setString(3, lname);
                statement.setString(4, password);
                statement.executeUpdate();
                showAlert("Success", "User registered successfully! You can now Login");

                closeSignUpWindow();
            }
        } catch (SQLException e) {

            if (e.getErrorCode() == 1062) { // Error code for unique constraint violation
               showAlert("Error", "Username already exists!");
            }
            else{
            e.printStackTrace();
            showAlert("Database Error", "Error connecting to the database");

            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void closeSignUpWindow() {
        // Get the current window (stage) and close it
        Stage stage = (Stage)FName.getScene().getWindow();
        stage.close();
    }
}