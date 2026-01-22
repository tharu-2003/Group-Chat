# Multi-User Group Chat Application

A real-time **Multi-User Group Chat Application** built using **JavaFX** for the interface and **Java Socket Programming** for network communication. This application supports multiple concurrent users, text messaging, and image sharing.



## 🚀 Features

* **Multi-Client Connectivity:** Multiple users can join the chat room simultaneously using a central Server.
* **Real-time Text Messaging:** Instant message delivery across all connected clients.
* **Image Sharing:** Users can select images (PNG/JPG) which are broadcasted to all participants.
* **Modern UI:** A clean, responsive interface built with JavaFX and FXML.
* **Message Alignment:** * **Right Side:** Sent messages (Green).
    * **Left Side:** Received messages (Yellow).
* **Multi-threading:** Ensures the UI remains responsive while the application listens for incoming data in the background.

---

## 📸 User Interface

### 1. Home / Login Page
The entry point where users can add their names to join the chat.
![Home Page](/src/main/resources/screenshots/home.png)

### 2. Server Dashboard
The central hub that monitors connections and broadcasts messages.
![Server Page](/src/main/resources/screenshots/server.png)

### 3. User Chat Window
The individual chat interface for users to communicate.
![User Page](/src/main/resources/screenshots/user.png)

---

## 🛠 Technologies Used

* **Language:** Java
* **GUI Framework:** JavaFX (with FXML)
* **Networking:** Java Sockets (TCP/IP)
* **Concurrency:** Java Threads
* **Build Tool:** Maven

---

## 🏗 Project Structure

The project follows a clean MVC-like pattern:

* **`AddUserInitializer.java`**: The main entry point to start the client application.
* **`ServerPageController.java`**: Manages the `ServerSocket`, client threads (`UserHandler`), and message/image broadcasting logic.
* **`UserPageController.java`**: Manages individual client connections, data input/output streams, and UI updates.
* **`AddUserPageController.java`**: Handles the logic for creating and launching new user windows.

---

## ⚙️ How to Run

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/tharu-2003/Group-Chat.git](https://github.com/tharu-2003/Group-Chat.git)
    ```
2.  **Start the Server:**
    Run the `ServerInitializer` or `ServerPageController` main method to start listening on port `4000`.
3.  **Start the Client:**
    Run `AddUserInitializer.java`.
4.  **Join the Chat:**
    Enter a username in the "Add User" window and click the **Add** button. You can open multiple instances to simulate different users.

---

## 📝 Author

* **Tharusha Sandaruwan** - [tharu-2003](https://github.com/tharu-2003)

---

## 📄 License
This project is open-source and available under the [MIT License](LICENSE).