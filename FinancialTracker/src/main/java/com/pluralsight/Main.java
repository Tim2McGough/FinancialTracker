package com.pluralsight;
import java.io.*;
import java.util.*;
import java.time.*;

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

                // Add the Transaction object to the list. Allows us to store it for future use ln9.
                transactions.add(transaction);
            }
            //You got to use a catch after a try if interacting with files.
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
                    addDeposit();  //Call addDeposit method
                    break;
                case "P":
                    makePayment();  // Call makePayment method
                    break;
                case "L":
                    displayLedger();  // Call the displayLedger method
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

    // Methods that we'll call on. First is for deposits.
    public static void addDeposit() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for deposit details
        String date = LocalDate.now().toString();  // Current date in YYYY-MM-DD format
        String time = LocalTime.now().toString();  // Current time in HH:MM:SS format

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
    // Next method is for  make payments.
    public static void makePayment() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for payment details, time is automatic.
        String date = LocalDate.now().toString();  // Current date in YYYY-MM-DD format
        String time = LocalTime.now().toString();  // Current time in HH:MM:SS format

        System.out.println("Enter payment description: ");
        String description = scanner.nextLine();

        System.out.println("Enter payment vendor: ");
        String vendor = scanner.nextLine();

        System.out.println("Enter payment amount: ");
        double amount = -Double.parseDouble(scanner.nextLine());  // Make the amount negative

        // Create a new Transaction object for the payment
        Transaction newPayment = new Transaction(date, time, description, vendor, amount);

        // Write the new payment to the CSV file
        try (FileWriter fwPayment = new FileWriter("transaction.csv", true);
             BufferedWriter bwPayment = new BufferedWriter(fwPayment);
             PrintWriter out = new PrintWriter(bwPayment)) {
            // Write the new payment to the CSV file in the same format
            out.println(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);
            System.out.println("Payment added and saved to file: " + newPayment);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the payment: " + e.getMessage());
        }
    }
        //displaying the ledger is different from the previous two.
    public static void displayLedger() {
        // We need access to the transactions list
        List<Transaction> transactions = loadTransactions();  // Load the transactions from the CSV

        System.out.println("Displaying all ledger entries (newest first):");

        // Loop through the list in reverse order to show the newest entries first
        // i is the size of our list -1 due to the count starting at 0
        // we go i-- each time to go to the next line. We get i print it and loop until done.

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            System.out.println(transaction);
        }
    }

    // The helper method to load transactions
    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try (FileReader frOne = new FileReader("transaction.csv");
             BufferedReader brOne = new BufferedReader(frOne)) {

            String line;
            while ((line = brOne.readLine()) != null) {
                if (line.startsWith("date")) {
                    continue;  // Skip the header line
                }

                // Split the line using |
                String[] parts = line.split("\\|");

                // Create a Transaction object. We have to do this again because the main method
                // runs automatically at the start, if we want users to be able to call up an ever updating list
                // we need the method to reload the list all over again.
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
        } catch (IOException e) {
            System.out.println("An error occurred while loading transactions: " + e.getMessage());
        }

        return transactions;
    }
}
