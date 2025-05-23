package com.ijse.gdse72.groupchatapplication;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server{

    static ServerSocket serverSocket;

    static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Waiting for connection...");

        new Thread(() -> {

            try {
                serverSocket = new ServerSocket(4000);

                while (true) {
                    Socket socket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(socket);
                    clients.add(clientHandler);
                    new Thread(clientHandler).start();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clients) {
            clientHandler.broadcastSend(message);
        }
    }

    public static void broadcastMessage(String message , ClientHandler client) {
        for (ClientHandler clientHandler : clients) {
            if(clientHandler != client) {
                clientHandler.broadcastSend(message);
            }
        }
    }



    //============================= inner class =================================

    public static class ClientHandler implements Runnable{

        Socket socket;
        DataInputStream dis;
        DataOutputStream dos;

        String inputMessage;
        String outputMessage;

        Scanner scanner = new Scanner(System.in);

        public ClientHandler(Socket socket) {
            try {
                this.socket = socket;
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            try {
                while (true) {



                    inputMessage = dis.readUTF();
                    System.out.println(inputMessage);
                    broadcastMessage(inputMessage , this);

                    System.out.print("Enter your Message :");
                    outputMessage = scanner.nextLine();
                    sendMessage(outputMessage);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    System.out.println("Bye");
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        public void broadcastSend(String message) {
            try {
                dos.writeUTF(message);
                dos.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void sendMessage(String message) {
            try {
                dos.writeUTF( "Server : " +message);
                dos.flush();

                broadcastMessage(message);

                System.out.println("Message sent");
                System.out.println("Your msg : " + message);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}