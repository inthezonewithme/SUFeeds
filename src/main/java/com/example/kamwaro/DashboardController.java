package com.example.kamwaro;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DashboardController {

    @FXML
    private AnchorPane classpane;

    @FXML
    private AnchorPane feedbackPane;
    @FXML
    private AnchorPane topicsPane;
    @FXML
    private VBox boxMenu;

    @FXML
    private Button btnSignOut;





    @FXML
    private Label lblNoClass;

    @FXML
    private Label HeaderClasses;

    public static String studentId;
    public static String fname;



    @FXML
    private Button AddClassButton;

    @FXML
    private Button AddButton;

    @FXML
    private TextField ClassNameField;

    @FXML
    private TextField ClassCodeField;

    @FXML
    private TextField TopicNameField;
    @FXML
    private TextField WeekNumberField;

    @FXML
    private TextField TopicField;
    @FXML
    private TextField FeedbackField;

    @FXML
    private HBox HBoxHeader;



    @FXML
    private VBox ClassVBox;
    private HBox selectedHBox;

    @FXML
    private VBox TopicVBox;

    @FXML
    private VBox FeedbackVBox;
    @FXML
    private VBox ReviewsVBox;



    // Setter method to receive student ID


    // Sign Out functionality
    public void handleSignOut() {
        showSignOutConfirmation();
    }

    public void showSignOutConfirmation() {
        // Create an alert with confirmation type
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sign Out Confirmation");
        alert.setHeaderText("You are about to sign out.");
        alert.setContentText("Are you sure you want to proceed?");

        // Show the alert and wait for the user's response
        Optional<ButtonType> result = alert.showAndWait();

        // Handle the user's response
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User chose "OK" - proceed with sign out
            SignOut();
        } else {
            // User chose "Cancel" or closed the dialog
            alert.close();
        }
    }

    @FXML
    private void SignOut() {
        Stage stage = (Stage) btnSignOut.getScene().getWindow();
        stage.close();
        try {
            // Load the login FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));  // Adjust the path to your login FXML
            Scene loginScene = new Scene(fxmlLoader.load(), 780, 460);

            // Create a new Stage (window) for the login screen
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(loginScene);
            loginStage.setResizable(false);

            // Show the login window
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error (could log it, show a message to the user, etc.)
        }
    }

    // Menu and Classes pane display functionality
    @FXML
    private void showMenuPane() {
        boxMenu.setVisible(true);
        classpane.setVisible(false);
        feedbackPane.setVisible(false);
        topicsPane.setVisible(false);
        loadStudentFeedback(studentId);


    }

    @FXML
    private void showClassesPane() {
        if (classpane == null) {
            System.out.println("classpane is null");
        } else {
            classpane.setVisible(true);
            boxMenu.setVisible(false);
            feedbackPane.setVisible(false);
            topicsPane.setVisible(false);
            refreshClassesVBox();
        }
    }
    @FXML
    private void showFeedbackPane(){
        if (feedbackPane == null) {
            System.out.println("feedbackPane is null");
        } else {
            classpane.setVisible(false);
            boxMenu.setVisible(false);
            topicsPane.setVisible(false);
            feedbackPane.setVisible(true);

            refreshFeedbackVBox();

        }
    }
    @FXML
    private void showTopicsPane(){
        if (topicsPane == null) {
            System.out.println("topicsPane is null");
        } else {
            classpane.setVisible(false);
            boxMenu.setVisible(false);
            feedbackPane.setVisible(false);
            topicsPane.setVisible(true);
            refreshTopicVBox();

        }
    }

    public void deleteSelectedClass() {
        if (selectedHBox != null) {
            ClassVBox.getChildren().remove(selectedHBox);  // Remove the selected HBox from the VBox
            selectedHBox = null;  // Clear the selected reference
        } else {
            System.out.println("No class selected to delete.");
            // Optionally, display a message to the user
        }
    }


    @FXML
    private void handleAddClasses() {
        try {
            // Load the add class FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addclass.fxml"));  // Adjust the path to your add class FXML
            Scene classScene = new Scene(fxmlLoader.load(), 321, 405);



            // Create a new Stage (window) for the add class screen
            Stage classStage = new Stage();
            classStage.setTitle("Add Class");
            classStage.setScene(classScene);
            classStage.setResizable(false);

            // Show the classes window
            classStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error (could log it, show a message to the user, etc.)
        }
    }
    @FXML
    private void handleAddClassButtonClick() {
        String className = ClassNameField.getText();
        String classCode = ClassCodeField.getText();

        // Validate inputs (you can add more validation if necessary)
        if (className.isEmpty() || classCode.isEmpty()) {
            // Show an alert if the inputs are invalid
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Please fill in both class name and class code.");
            alert.showAndWait();
            return;
        }

        // Insert the class into the database
        if (insertClassIntoDatabase(className, classCode)) {
            // After successful insertion, refresh the VBox with the updated list of classes

            refreshClassesVBox();

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Class Added");
            successAlert.setHeaderText(null);
            successAlert.setContentText("The class '" + className + "' has been successfully added.");
            successAlert.showAndWait();

        } else {
            // Show an alert if the insertion failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Failed to add class.");
            alert.showAndWait();
        }
    }

    private List<ClassModel> getClassesForStudent(String studentId) {
        List<ClassModel> classes = new ArrayList<>();
        String query = "SELECT * FROM tbl_class WHERE Student_Number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String className = rs.getString("Class_Name");
                String classCode = rs.getString("Class_ID");

                // Create a ClassModel instance for each class
                ClassModel classModel = new ClassModel(className, classCode);
                classes.add(classModel);  // Add to the list
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classes;  // Return the list of ClassModel objects
    }

    @FXML
    private void refreshClassesVBox() {
        if (ClassVBox == null) {
            System.out.println("ClassVBox is not initialized yet.");
            return;  // Early return if ClassVBox is not ready
        }
        ClassVBox.getChildren().clear(); // Clear current items before refreshing

        // Retrieve the list of classes (assuming a `getClasses()` method exists that queries the database)
        List<ClassModel> classes = getClassesForStudent(studentId);

        for (ClassModel classModel : classes) {
            try {
                // Load the ClassCard FXML for each class
                FXMLLoader loader = new FXMLLoader(getClass().getResource("classcard.fxml"));
                HBox classCard = loader.load();

                // Get the controller for ClassCard and set class details

                classcardController controller = loader.getController();
                controller.setClassDetails(classModel.getClassName(), classModel.getClassCode());

                // Add the card to the VBox
                ClassVBox.getChildren().add(classCard);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle any loading errors here
            }
        }
    }
    @FXML
    private boolean insertClassIntoDatabase(String className, String classCode) {
        String query = "INSERT INTO tbl_class (Class_ID, Class_Name, Student_Number) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Assuming studentId is set earlier in the controller
            pstmt.setString(1, classCode);
            pstmt.setString(2, className);
            pstmt.setString(3, studentId);  // Assuming this was set earlier (for the logged-in student)

            int rowsAffected = pstmt.executeUpdate();  // Execute the insert query

            return rowsAffected > 0;  // Return true if the insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();  // Handle the error as needed
            return false;
        }
    }


    @FXML
    private void handleAddTopics(){
        try {
            // Load the add class FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addtopic.fxml"));  // Adjust the path to your add class FXML
            Scene classScene = new Scene(fxmlLoader.load(), 321, 405);



            // Create a new Stage (window) for the add class screen
            Stage topicStage = new Stage();
            topicStage.setTitle("Add Topic");
            topicStage.setScene(classScene);
            topicStage.setResizable(false);

            // Show the classes window
            topicStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error (could log it, show a message to the user, etc.)
        }
    }
    @FXML
    private void handleAddTopicButtonClick(){
    String topicName = TopicNameField.getText();
    String weekNumber = WeekNumberField.getText();

    if(topicName.isEmpty() || weekNumber.isEmpty()){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText("Please fill in both topic name and week number.");
        alert.showAndWait();
        return;

        }
        if (insertTopicIntoDatabase(topicName, weekNumber)) {
            // After successful insertion, refresh the VBox with the updated list of classes

            refreshTopicVBox();

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Topic Added");
            successAlert.setHeaderText(null);
            successAlert.setContentText("The topic '" + topicName + "' has been successfully added.");
            successAlert.showAndWait();

        } else {
            // Show an alert if the insertion failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Failed to add topic.");
            alert.showAndWait();
        }

    }

    @FXML
    private void refreshTopicVBox() {
        if (TopicVBox == null) {
            System.out.println("TopicVBox is not initialized yet.");
            return;  // Early return if TopicVBox is not ready
        }

        TopicVBox.getChildren().clear(); // Clear current items before refreshing

        // Retrieve the list of topics for the student
        List<TopicModel> topics = getTopicsForStudent(studentId);

        for (TopicModel topicModel : topics) {
            try {
                // Load the ClassCard FXML for each class
                FXMLLoader loader = new FXMLLoader(getClass().getResource("topiccard.fxml"));
                HBox topicCard = loader.load();

                // Get the controller for ClassCard and set class details

                topiccardController controller = loader.getController();
                controller.setTopicDetails(topicModel.getTopicName(), topicModel.getWeekNumber());



                // Add the card to the VBox
                TopicVBox.getChildren().add(topicCard);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle any loading errors here
            }
        }
    }

    @FXML
    private boolean insertTopicIntoDatabase(String topicName, String week_number){
        String query = "INSERT INTO tbl_topic (Topic_Name, Week_Number, Student_Number) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Assuming studentId is set earlier in the controller
            pstmt.setString(1, topicName);
            pstmt.setString(2, week_number);
            pstmt.setString(3, studentId);  // Assuming this was set earlier (for the logged-in student)

            int rowsAffected = pstmt.executeUpdate();  // Execute the insert query

            return rowsAffected > 0;  // Return true if the insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();  // Handle the error as needed
            return false;
        }
    }
    private List<TopicModel> getTopicsForStudent(String StudentId){
        List<TopicModel> topics = new ArrayList<>();
        String sql = "SELECT * FROM tbl_topic where Student_Number = ?";

        try(Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement psmt = conn.prepareStatement(sql);

            psmt.setString(1, StudentId);
            ResultSet res = psmt.executeQuery();

            while(res.next()){
                String topicName = res.getString("Topic_Name");
                String weekNumber = res.getString("Week_Number");

                TopicModel topicModel = new TopicModel(topicName,weekNumber);
                topics.add(topicModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topics;
    }


    @FXML
    private void handleAddFeedback(){
        try {
            // Load the add class FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addfeedback.fxml"));  // Adjust the path to your add class FXML
            Scene classScene = new Scene(fxmlLoader.load(), 321, 405);



            // Create a new Stage (window) for the add class screen
            Stage feedbackStage = new Stage();
            feedbackStage.setTitle("Add Feedback");
            feedbackStage.setScene(classScene);
            feedbackStage.setResizable(false);

            // Show the classes window
            feedbackStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error (could log it, show a message to the user, etc.)
        }
    }
    @FXML
    private void handleAddFeedbackButtonClick(){
        String topicName = TopicField.getText();
        String comment = FeedbackField.getText();

        if(topicName.isEmpty() || comment.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Please fill in both topic name and Feedback.");
            alert.showAndWait();
            return;

        }
        if (insertFeedbackIntoDatabase(topicName, comment)) {
            // After successful insertion, refresh the VBox with the updated list of classes

            refreshFeedbackVBox();

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Feedback Added");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Thank you for your comment");
            successAlert.showAndWait();

        } else {
            // Show an alert if the insertion failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Failed to add Feedback.");
            alert.showAndWait();
        }

    }
    @FXML
    private void refreshFeedbackVBox(){
        if (FeedbackVBox == null) {
            System.out.println("FeedbackVBox is not initialized yet.");
            return;  // Early return if TopicVBox is not ready
        }

        FeedbackVBox.getChildren().clear(); // Clear current items before refreshing

        // Retrieve the list of topics for the student
        List<FeedbackModel> feedback = getFeedbackForStudent(studentId);

        for (FeedbackModel feedbackModel : feedback) {
            try {
                // Load the ClassCard FXML for each class
                FXMLLoader loader = new FXMLLoader(getClass().getResource("feedbackcard.fxml"));
                HBox topicCard = loader.load();

                // Get the controller for ClassCard and set class details

                feedbackcardController controller = loader.getController();
                controller.setFeedbackDetails(feedbackModel.getTopicName(), feedbackModel.getComment());



                // Add the card to the VBox

                FeedbackVBox.getChildren().add(topicCard);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle any loading errors here
            }
        }
    }
    @FXML
    private boolean insertFeedbackIntoDatabase(String topicName, String comment){
        String query = "INSERT INTO tbl_feedback (Topic_Name, Comment, Student_Number, First_Name) VALUES (?, ?, ?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Assuming studentId is set earlier in the controller
            pstmt.setString(1, topicName);
            pstmt.setString(2, comment);
            pstmt.setString(3, studentId);
            pstmt.setString(4, fname);// Assuming this was set earlier (for the logged-in student)

            int rowsAffected = pstmt.executeUpdate();  // Execute the insert query

            return rowsAffected > 0;  // Return true if the insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();  // Handle the error as needed
            return false;
        }
    }
    private List<FeedbackModel> getFeedbackForStudent(String StudentId) {
        List<FeedbackModel> feedback = new ArrayList<>();
        String sql = "SELECT * FROM tbl_feedback where Student_Number = ?";

        try(Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement psmt = conn.prepareStatement(sql);

            psmt.setString(1, StudentId);
            ResultSet res = psmt.executeQuery();

            while(res.next()){
                String topicName = res.getString("Topic_Name");
                String comment = res.getString("Comment");

                FeedbackModel feedbackModel = new FeedbackModel(topicName,comment);
                feedback.add(feedbackModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedback;
    }

    public List<ReviewsModel> getFeedbackByStudentNumber(String studentNumber) {
        List<ReviewsModel> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM tbl_feedback";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {


            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Create a ReviewsModel for each feedback entry
                ReviewsModel feedback = new ReviewsModel(
                        rs.getString("First_Name"),
                        rs.getString("Comment")
                );
                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackList;
    }

    public void loadStudentFeedback(String studentNumber) {
        // Clear any existing feedback cards
        ReviewsVBox.getChildren().clear();

        // Fetch feedback entries for the student
        List<ReviewsModel> feedbackList = getFeedbackByStudentNumber(studentNumber);

        for (ReviewsModel feedback : feedbackList) {
            try {
                // Load the feedback card FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("reviewscard.fxml"));
                HBox feedbackCard = loader.load();

                // Get the controller of the feedback card and set the feedback data
                reviewscardController cardController = loader.getController();
                cardController.setFeedbackData(feedback);

                // Add the card to the VBox
                ReviewsVBox.getChildren().add(feedbackCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
