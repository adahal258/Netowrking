// Name: Aashutosh Dahal
// Class: 2251: A01
// Assignment:Network Part 2.
// Purpose: To create Client class that creates a client-side GUI and have textField to
// take input of file from the user and extract two matrices and pass it to server to print the information.
// Filename: "Client.java"

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    public void runClient() {
        JFrame clientSideFrame = new JFrame(); //creating a frame

        JLabel heading = new JLabel();
        heading.setText("Welcome to Matrix Addition Server-Client Model"); //heading
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        Font font = new Font("Arial", Font.BOLD, 15);
        heading.setFont(font);

        JPanel panel = new JPanel();

        JLabel info = new JLabel("Please enter your filename:");


        JTextField fileField = new JTextField(15);
        //This is to print anything that user type in text-field in Command Prompt.
//        fileField.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                System.out.println("Successfully send file:- "+ fileField.getText());
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//
//            }
//        });

        JButton submit = new JButton("Submit");
        JTextArea solution = new JTextArea(30,30);

        panel.add(heading);
        panel.add(info);
        panel.add(fileField);
        panel.add(submit);

        //setting up the windows......
        clientSideFrame.setSize(400, 400);
        clientSideFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientSideFrame.getContentPane().add(panel);
        clientSideFrame.setLocationRelativeTo(null);
        clientSideFrame.setVisible(true);
        clientSideFrame.setResizable(false);

        submit.addActionListener(new ActionListener() { //action listener for Submit....
            @Override
            public void actionPerformed(ActionEvent e) {
                String matrixFile = fileField.getText();
                if(matrixFile.equals("TERMINATE"))
                    System.exit(1);
                System.out.println(matrixFile);
                fileField.setText("");
                File file = new File(matrixFile);
                int[][] result = serverConnection(file);
                StringBuilder resultS = convertMatrixToString(result);
                solution.setText(resultS.toString());
            }
        });
        panel.add(solution); //adding solution
    }
    /*
    method to establish the connection between server and client when the file is sent.
     */
    public static int[][] serverConnection(File file){
            Scanner fileIn = null;
            int[][] result= null;
            try {
                fileIn = new Scanner(file);
                JOptionPane.showMessageDialog(null, "File Detected");
                String rowColumn = fileIn.nextLine();
                int row = Integer.parseInt(rowColumn.substring(0, 1));
                int column = Integer.parseInt(rowColumn.substring(2)); //since index 1 contains space.

                int[][] matrix1FromFile;
                int[][] matrix2FromFile;

                matrix1FromFile = matrixFromFile(row, column, fileIn);
                matrix2FromFile = matrixFromFile(row, column, fileIn);
                System.out.println("Attempting connection\n");
                Socket client = null;
                try {
                    client = new Socket("LocalHost", 4400);
                    System.out.println("Connected to port 4400");
                    JOptionPane.showMessageDialog(null, "Connected:Successfully send to server.");
                    Server server = new Server(matrix1FromFile, matrix2FromFile);
                    result = server.getAdditionResult();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,"Server not Started");
                    System.out.println("Connection not found.");
                    System.exit(1);
                    throw new RuntimeException(ex);
                }

            } catch (IOException err) {
                System.out.println("error IO exception: " + err.getMessage());
                JOptionPane.showMessageDialog(null, "No such file found.");
                System.exit(1);
            }
            return result;
        }

    //methods that take three parameters which is used to read matrix from the file.
    // int rows, int columns, Scanner file_reader
    //Return the matrix read from file_reader.
    public static int[][] matrixFromFile(int rows, int columns, Scanner file_reader) {
        int[][] matrix = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            String[] matrixData = file_reader.nextLine().split(" ");
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = Integer.parseInt(matrixData[j]);
            }
        }
        return matrix;
    }

    /*
    This methods converts matrix to string that is then add to JTextField as the result of
    matrix addition
     */
    public static StringBuilder convertMatrixToString(int[][] matrix){
        StringBuilder result = new StringBuilder();
        for (int[] rows : matrix) {
            for (int data : rows) {
                System.out.printf("%-4d",data); // print in console.
                result.append(String.format("%-4d",data)); //add to result for GUI representation.
            }
            System.out.println();  //separating row and column.
            result.append("\n");
        }
        return result;
    }

}
