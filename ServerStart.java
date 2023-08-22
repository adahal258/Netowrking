// Name: Aashutosh Dahal
// Class: 2251: A01
// Assignment:Network Part 2.
// Purpose: To create Server start class that starts the server at 4400 and also for runs the server continuously
// waiting for future clients.
// Filename: "ServerStart.java"
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerStart {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(4400);
        System.out.println("Server started and listening on port 4400");

        while (true) {
            // Wait for a client to connect
            Socket clientSocket = server.accept();
            System.out.println("New client connected.");

            // Start a new thread to handle the communication with the client
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            clientHandler.start();
        }
    }

    /*
    This method runs the server continuously and waits for future clients to join the server.
     */
    private static class ClientHandler extends Thread {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error handling client connection: " + e.getMessage());
            }
        }
    }
}