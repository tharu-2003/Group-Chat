module com.ijse.gdse72.groupchatapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ijse.gdse72.groupchatapplication to javafx.fxml;
    exports com.ijse.gdse72.groupchatapplication;
//    exports com.ijse.gdse72.groupchatapplication.controller;
    opens com.ijse.gdse72.groupchatapplication.controller to javafx.fxml;
}