package com.ijse.gdse72.groupchatapplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    static String clientName;
    Socket socket;
    DataOutputStream dos;
    DataInputStream dis;

    String inputMsg;
    String outputMsg;

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Your name: ");
        clientName = sc.nextLine();
        System.out.println(clientName + " added");

        ClientPageController controller = new ClientPageController();
        controller.setClientName(clientName);

    }


    public void setClientName(String clientName) {
        this.clientName = clientName;
        clientInitializer();
    }

    public void clientInitializer() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost" , 4000);
                dos = new DataOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());

                while (true) {
                    System.out.print("Enter your Message :");
                    outputMsg = scanner.nextLine();
                    String ok = sendMessage(outputMsg);

                    String msg = dis.readUTF();
                    System.out.println(msg);



                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                System.out.println("Bye");
                scanner.close();
            }
        }).start();
    }

    public String sendMessage(String msg) {
        try {
            dos.writeUTF(clientName + " : " + msg);
            dos.flush();
            System.out.println("Your msg : " + msg);
            return "Ok";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
