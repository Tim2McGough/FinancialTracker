package com.pluralsight;
import java.io.*;

public class Main {
    public static void main(String[] args){
try{
// First create a file reader which opens up the csv and can read it char by char then create buff which reads by line
FileReader frOne = new FileReader("transaction.csv");
BufferedReader brOne = new BufferedReader(frOne);

//handle any exceptions using the Catch command
} catch (IOException e) {
        System.out.println("an error occured " + e.getMessage());
}
}
}