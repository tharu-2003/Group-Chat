package com.ijse.gdse72.testchat.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class UserPageController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnSend;

    @FXML
    private ImageView imgView;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private TextField txtMsg;

    @FXML
    private VBox vbox;

    String userName;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private File file;

    public void setUserName(String userName) {
        this.userName = userName;
        userInitializer();
    }

    public void userInitializer() {
        new Thread(() ->{
            try {
                socket = new Socket("localhost" , 4000);
                dos = new DataOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());

                while (true) {

                    String msg = dis.readUTF();

                        if (msg.equals("IMAGE")){

                            int length = dis.readInt();
                            byte[] imgBytes = new byte[length];
                            dis.readFully(imgBytes);
                            ByteArrayInputStream bais = new ByteArrayInputStream(imgBytes);

                            Platform.runLater(()->{
                                Image image = new Image(bais);

                                ImageView imageView = new ImageView(image);
                                imageView.setFitWidth(100);
                                imageView.setFitHeight(100);
                                imageView.setPreserveRatio(true);

                                HBox hbox = new HBox(imageView);
                                hbox.setAlignment(Pos.CENTER_LEFT);

                                vbox.getChildren().add(hbox);
                                scrollpane.setVvalue(1.0);
                            });

                        }else {
                            Platform.runLater(() ->{
                                Label label = new Label(msg);

                                label.setPadding(new Insets(5));
                                label.setStyle("-fx-background-color: #fbe93a; -fx-background-radius: 10");

                                HBox hbox = new HBox(label);
                                hbox.setAlignment(Pos.CENTER_LEFT);
                                vbox.getChildren().add(hbox);

                                scrollpane.setVvalue(1.0);
                            });
                        }


                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @FXML
    void addOnAction(ActionEvent event) {
        FileChooser fileChooser =new FileChooser();

        file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            Image image =new Image(file.toURI().toString());
            imgView.setImage(image);
            imgView.setPreserveRatio(true);
        }
    }

    @FXML
    void sendOnAction(ActionEvent event) {
        if(imgView.getImage() != null) {

            try {
                byte[] imgbytes = Files.readAllBytes(file.toPath());

                dos.writeUTF("IMAGE");
                dos.writeInt(imgbytes.length);
                dos.write(imgbytes);
                dos.flush();

                Platform.runLater(() -> {
                    Image image =new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    imageView.setPreserveRatio(true);

                    HBox hbox = new HBox(imageView);
                    hbox.setAlignment(Pos.CENTER_RIGHT);

                    vbox.getChildren().add(hbox);
                    scrollpane.setVvalue(1.0);

                });
                imgView.setImage(null);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }else {
            String msg = txtMsg.getText();
            try {
                dos.writeUTF(userName+" : " +msg);
                dos.flush();

                Platform.runLater(() ->{
                    Label label = new Label(msg);

                    label.setPadding(new Insets(5));
                    label.setStyle("-fx-background-color: #4de64d; -fx-background-radius: 10");

                    HBox hbox = new HBox(label);
                    hbox.setAlignment(Pos.CENTER_RIGHT);
                    vbox.getChildren().add(hbox);

                    scrollpane.setVvalue(1.0);

                    txtMsg.clear();
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
