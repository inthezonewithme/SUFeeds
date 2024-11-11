package com.example.kamwaro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginForm extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Dashboard.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 780, 460);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false); // Prevents resizing of the entire window
        stage.show();

    }
}
