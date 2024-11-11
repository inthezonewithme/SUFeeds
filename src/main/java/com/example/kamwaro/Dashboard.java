package com.example.kamwaro;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Dashboard extends Application {
    private static String username; // Declare a static variable to hold the username

    public static void setUsername(String username) {
        Dashboard.username = username;
    }




    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Dashboard.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();

        // Get the controller from the FXML loader
        DashboardController controller = fxmlLoader.getController();
        // Pass the username to the controller


        Scene scene = new Scene(root, 1230, 680);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}