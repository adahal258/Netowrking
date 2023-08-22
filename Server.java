// Name: Aashutosh Dahal
// Class: 2251: A01
// Assignment:Network Part 2.
// Purpose: To create Server class that takes two matrices in its constructor and print it in GUI as well as CUI.
// Filename: "Server.java"
import javax.swing.*;
public class Server {
    static StringBuilder result = new StringBuilder(); //store resulting matrix
    int[][] additionResult;
    public Server(int[][] matrixA, int[][]matrixB) {
        result.append("The matrix A is: \n");
        System.out.println("The matrix A is: ");
        print2dArray(matrixA);
        System.out.println();

        result.append("\nThe matrix B is: \n");
        System.out.println("The matrix B is: ");
        print2dArray(matrixB);
        System.out.println();

        //addition operation using ThreadOperation
        additionResult = new int[matrixA.length][matrixA[0].length];
        ThreadOperation threadOperation1 = new ThreadOperation(matrixA,matrixB, additionResult,"UpperLeft");
        ThreadOperation threadOperation2 = new ThreadOperation(matrixA,matrixB, additionResult,"UpperRight");
        ThreadOperation threadOperation3 = new ThreadOperation(matrixA,matrixB, additionResult,"LowerLeft");
        ThreadOperation threadOperation4 = new ThreadOperation(matrixA,matrixB, additionResult,"LowerRight");
        threadOperation1.start();
        threadOperation2.start();
        threadOperation3.start();
        threadOperation4.start();

        try {
            threadOperation1.join();
            threadOperation2.join();
            threadOperation3.join();
            threadOperation4.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null,"The two matrix from file\n"+result+"\nPlease press OK to see the result"); //GUI representation

        result.setLength(0); //resetting the string builder.
    }
    //methods that takes 2-d array (matrix) and print it with specified format.
    // return type is void
    public static void print2dArray(int[][] matrix){
        for (int[] rows : matrix) {
            for (int data : rows) {
                System.out.printf("%-4d",data);
                result.append(String.format("%-4d",data)); //add to result for GUI representation.
            }
            System.out.println();  //separating row and column.
            result.append("\n");
        }
    }
    /*
    This method is getter to get the value of the resultant matrix.
     */
    public int[][] getAdditionResult() {
        return additionResult;
    }
}
