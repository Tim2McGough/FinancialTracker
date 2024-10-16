package com.pluralsight;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // This creates an array called transactions. Basically a list that holds Transaction Objects
        List<Transaction> transactions = new ArrayList<>();

        // Step 1: Load transactions from the CSV file.
        // We want to Try Catch anytime we're dealing with files in case of error.
        try (FileReader frOne = new FileReader("transaction.csv");
             BufferedReader brOne = new BufferedReader(frOne)) {

            String line;
            // While line is not empty, print out that line. If line is empty, move on.
            while ((line = brOne.readLine()) != null) {
                // The header needs to be skipped. This works but there might be a better way in case file is changed later.
                if (line.startsWith("date")) {
                    continue;  // Skip to the next line
                }

                // Split the data using | as that's how it is in the example
                String[] parts = line.split("\\|");
                Transaction transaction = new Transaction(
                        parts[0],  // Date
                        parts[1],  // Time
                        parts[2],  // Description
                        parts[3],  // Vendor
                        Double.parseDouble(parts[4])  // Amount
                );

                // Add the Transaction object to the list
                transactions.add(transaction);
            }
            //You got to use a catch after a try. It's in case there's an error.
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        // Step 2: The home screen loop
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;  // This allows the screen to continue displaying until we mark it as true!
        while (!exit) {
            // Display the home screen options
            System.out.println("HOME SCREEN");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine().toUpperCase();
            // placeholder switch options
            switch (option) {
                case "D":
                    addDeposit();  // This will be implemented soon
                    break;
                case "P":
                    makePayment();  // This will be implemented soon
                    break;
                case "L":
                    displayLedger();  // This will be implemented soon
                    break;
                case "X":
                    exit = true;
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Methods that we'll call on.
    public static void addDeposit() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for deposit details
        System.out.println("Enter deposit date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.println("Enter deposit time (HH:MM:SS): ");
        String time = scanner.nextLine();

        System.out.println("Enter deposit description: ");
        String description = scanner.nextLine();

        System.out.println("Enter deposit vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter deposit amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        // Create a new Transaction object for the deposit
        Transaction newDeposit = new Transaction(date, time, description, vendor, amount);
        // We're dealing with files again (writing to one) so try catch is needed.
        try (FileWriter fwDeposit = new FileWriter("transaction.csv", true);
             BufferedWriter bwDeposit = new BufferedWriter(fwDeposit);
             PrintWriter out = new PrintWriter(bwDeposit)) {
            // Write the new transaction to the CSV file in the same format
            out.println(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);
            System.out.println("Deposit added and saved to file: " + newDeposit);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the deposit: " + e.getMessage());
        }
    }


    public static void makePayment() {
        System.out.println("Making a payment...");
    }

    public static void displayLedger() {
        System.out.println("Displaying the ledger...");
    }
}
