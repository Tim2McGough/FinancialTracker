package com.pluralsight;


// Here I am creating a class called Transaction. The variables listed make up what a "Transaction" is
public class Transaction {
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    // Constructor Sets the initial values that make up a Transaction and allow it to be called on later.
    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // Getters
    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    // toString allows you to print in a way that makes sense to humans i.e. a string rather than objects memory location
    public String toString() {
        return date + " | " + time + " | " + description + " | " + vendor + " | " + amount;
    }
}
