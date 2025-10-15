package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LedgerApp {
    static final String fileName = "transactions.csv";
    static final Scanner scanner = new Scanner(System.in);
    private List<Transaction> allTransactions = new ArrayList<>();
    public static void main(String[] args) {
        while (true) {
            menuScreen();
            String choice = scanner.nextLine().toUpperCase();
            switch (choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    loadTransactions();
                    break;
                case "X":
                    System.out.println("Exiting application...Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }
    }

        static void menuScreen() {
            System.out.println("--- Welcome to the Financial Ledger App ---");
            System.out.println("\n--- Home Screen ---");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.println("Enter Choice: ");
        }
    }
        // Core Data Operations

        private void loadTransactions() {
            al
        }
