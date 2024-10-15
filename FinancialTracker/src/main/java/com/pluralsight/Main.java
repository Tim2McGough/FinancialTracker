package com.pluralsight;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Try catch ensures the file reader and buffered reader are closed after use
        try (FileReader frOne = new FileReader("transaction.csv");
             BufferedReader brOne = new BufferedReader(frOne)) {

            String line;
            // While line is not empty, print out that line. If line is empty, move on.
            while ((line = brOne.readLine()) != null) {
                // Split the data using | as that's how it is in the example
                String[] parts = line.split("\\|");

                for (String part : parts) {
                    System.out.println(part);  // Print each part on a new line
                }
                System.out.println("<-*-*-*-*->");  // Separator for each line of the file. With some fun flair.
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
