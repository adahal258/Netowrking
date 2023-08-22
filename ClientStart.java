// Name: Aashutosh Dahal
// Class: 2251: A01
// Assignment:Network Part 2.
// Purpose: To create ClientStart class that call a client-side GUI and have textField to
// take input of file from the user and extract two matrices and pass it to server to print the information.
// Filename: "Client.java"

import java.io.IOException;

public class ClientStart {
    public static void main(String[] args) throws IOException {
        Client c;
        c = new Client();
        c.runClient();
    }
}
