module com.ijse.gdse72.testchat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ijse.gdse72.testchat to javafx.fxml;
    exports com.ijse.gdse72.testchat;
    exports com.ijse.gdse72.testchat.controller;
    opens com.ijse.gdse72.testchat.controller to javafx.fxml;
}