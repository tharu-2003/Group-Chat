package com.ijse.gdse72.testchat.controller;

import com.ijse.gdse72.testchat.ServerInitializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class AddUserPageController {

    @FXML
    private Button btnAdd;

    @FXML
    private TextField txtName;

    String userName;
    ArrayList<String> users = new ArrayList<>();

    @FXML
    void addOnAction(ActionEvent event) throws IOException {

        userName = txtName.getText();

        FXMLLoader fxmlLoader = new FXMLLoader(AddUserPageController.class.getResource("/view/UserPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        users.add(userName);
        UserPageController userPageController = fxmlLoader.getController();
        userPageController.setUserName(userName);

        Stage stage = new Stage();
        stage.setTitle(userName);
        stage.setScene(scene);
        stage.show();

        txtName.clear();
    }

}
