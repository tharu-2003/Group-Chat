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
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ServerPageController {

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

    private ServerSocket serverSocket;
    private File file;

    private static List<UserHandler> users = new ArrayList<>();

    public void initialize() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(4000);

                while (true) {
                    Socket socket = serverSocket.accept();
                    UserHandler userHandler = new UserHandler(socket);
                    users.add(userHandler);
                    new Thread(userHandler).start();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @FXML
    void addOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imgView.setImage(image);
            imgView.setPreserveRatio(true);
        }
    }

    @FXML
    void sendOnAction(ActionEvent event) {

        if(imgView.getImage() != null) {

            try {
                byte[] bytes = Files.readAllBytes(file.toPath());

                broadcastImg(bytes);

                Platform.runLater(() -> {
                    Image image = new Image(file.toURI().toString());

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

        }else{
            String msg = txtMsg.getText();

            broadcastMsg(msg);

            Platform.runLater(() -> {
                Label label = new Label(msg);

                label.setPadding(new Insets(5));
                label.setStyle("-fx-background-color: #4de64d; -fx-background-radius: 10");

                HBox hbox = new HBox(label);
                hbox.setAlignment(Pos.CENTER_RIGHT);
                vbox.getChildren().add(hbox);

                scrollpane.setVvalue(1.0);

                txtMsg.clear();
            });

        }

    }

    public void broadcastMsg(String msg,UserHandler user) {
        for (UserHandler userHandler : users) {
            if (userHandler != user) {
                userHandler.sendMsg(msg);
            }
        }

    }

    public void broadcastMsg(String msg) {
        for (UserHandler userHandler : users) {

                userHandler.sendMsg("Server : " + msg);

        }
    }

    public void broadcastImg(byte[] imgbytes , UserHandler user) {
        for (UserHandler userHandler : users) {
            if (userHandler != user) {
                userHandler.sendImg(imgbytes);
            }
        }
    }

    public void broadcastImg(byte[] imgbytes) {
        for (UserHandler userHandler : users) {

                userHandler.sendImg(imgbytes);

        }
    }


    //========================================================================

    public class UserHandler implements Runnable {

        Socket socket;
        DataOutputStream dos;
        DataInputStream dis;

        public UserHandler(Socket socket) throws IOException {
            this.socket = socket;
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        }

        public void sendMsg(String msg) {
            try {
                dos.writeUTF(msg);
                dos.flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void sendImg(byte[] imgbytes) {
            try {
                dos.writeUTF("IMAGE");
                dos.writeInt(imgbytes.length);
                dos.write(imgbytes);
                dos.flush();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String msg = dis.readUTF();

                    if (msg.equals("IMAGE")) {

                        int length = dis.readInt();
                        byte[] bytes = new byte[length];
                        dis.readFully(bytes);
                        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

                        Platform.runLater(() -> {
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
                        broadcastImg(bytes , this);

                    }else {
                        Platform.runLater(() -> {
                            Label label = new Label(msg);

                            label.setPadding(new Insets(5));
                            label.setStyle("-fx-background-color: #fbe93a; -fx-background-radius: 10");

                            HBox hBox = new HBox(label);
                            hBox.setAlignment(Pos.CENTER_LEFT);
                            vbox.getChildren().add(hBox);

                            scrollpane.setVvalue(1.0);
                        });
                        broadcastMsg(msg , this);
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

        }
    }

}
