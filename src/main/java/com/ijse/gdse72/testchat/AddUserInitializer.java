package com.ijse.gdse72.testchat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddUserInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AddUserInitializer.class.getResource("/view/AddUserPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Add the Chat");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
