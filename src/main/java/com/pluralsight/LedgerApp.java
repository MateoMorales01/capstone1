package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class LedgerApp {

    public static void main(String[] args) throws IOException {
        String fileName = "transactions.csv";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            menuScreen();
            String choice = scanner.nextLine().toUpperCase();
            switch (choice) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    System.out.println("A) All Entries");
                    System.out.println("D) Deposits Only");
                    System.out.println("P) Payments Only");
                    System.out.println("R) Reports");
                    System.out.println("H) Home");
                    System.out.print("\nChoose an option: ");
                    String userLedgerChoice = scanner.nextLine().trim().toUpperCase();

                    switch (userLedgerChoice) {
                        case "A":
                            commitAllEntries();
                            break;
                        case "D":
                            addingDeposit();
                            break;
                        case "P":
                            makePayments();
                            break;
                        case "R":
                            System.out.println("1) Month To Date");
                            System.out.println("2) Previous Month");
                            System.out.println("3) Year To Date");
                            System.out.println("4) Previous Year");
                            System.out.println("5) Search by Vendor");
                            System.out.println("0) Back");
                            int userReportChoice = scanner.nextInt();
                            switch (userReportChoice) {
                                case 1 :
                                    LocalDate now = LocalDate.now();
                                    LocalDate startOfMonth = now.withDayOfMonth(1);

                                    System.out.println("\n--- MONTH TO DATE REPORT ---");
                                    System.out.println("Period: " + startOfMonth + " to " + now);
                                    ArrayList<Transaction> transactions = loadTransactions();
                                    for (Transaction item : transactions) {
                                        if (item.getTransactionDate().isAfter(startOfMonth) && item.getTransactionDate().isBefore(now)) {
                                            System.out.println(item.display());
                                        }
                                    }
                                    break;
                            }

                    }
                    break;
                case "X":
                    System.out.println("Exiting application...Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;

            }
        }
    }

    private static void makePayments() {
        System.out.println("Payments only");
        ArrayList<Transaction> transactions = loadTransactions();
        for (Transaction item : transactions) {
            if (item != null) {
                if (item.getAmount() < 0) {
                    System.out.println(item.display());
                }
            }
        }
    }

    private static void addingDeposit() {
        System.out.println("Deposits only");
        ArrayList<Transaction> transactions = loadTransactions();
        for (Transaction item : transactions) {
            if (item != null) {
                if (item.getAmount() > 0) {
                    System.out.println(item.display());
                }
            }
        }
    }

    private static void commitAllEntries() {
        System.out.println("Printing all Entries");
        ArrayList<Transaction> transactions = loadTransactions();
        for (Transaction item : transactions) {
            if (item != null) {
                System.out.println(item.display());
                }
        }
    }

    private static void addPayment(Scanner scanner) {
        try {
            System.out.println("Enter description: ");
            String description = scanner.nextLine();

            System.out.println("Enter Vendor: ");
            String vendor = scanner.nextLine();

            System.out.println("Enter amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, -amount);
            saveTransaction(transaction);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addDeposit(Scanner scanner) {
        try {
            System.out.println("Enter description: ");
            String description = scanner.nextLine();

            System.out.println("Enter Vendor: ");
            String vendor = scanner.nextLine();

            System.out.println("Enter amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
            saveTransaction(transaction);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
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

    //

    private void ledgerMenu() {

        while (true) {
            System.out.println("\n=== Ledger Menu ===");
            System.out.println("(T) All Transactions");
            System.out.println("(D) Deposits");
            System.out.println("(P) Payments");
            System.out.println("(R) Reports");
            System.out.println("(X) Back to Main Menu");
            System.out.println("Choose an option: ");
            Scanner scanner = null;
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {

            }


        }

    }

    public static Transaction fromCsvLine(String line) {
        //Splitting different variables apart
        String[] parts = line.split("\\|");
        if (parts.length != 5) return null;

        try {
            LocalDate date = LocalDate.parse(parts[0]);
            LocalTime time = LocalTime.parse(parts[1]);
            String description = parts[2];
            String vendor = parts[3];
            double amount = Double.parseDouble(parts[4]);
            return new Transaction(date, time, description, vendor, amount);
        } catch (Exception e) {
            return null;
        }
    }
    private static void saveTransaction(Transaction transaction) {
        //Updating Date/Time formatter
        try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            buffWriter.write("\n"+ transaction.writeTocsv());
        } catch (IOException e) {
            System.out.println("Error saving transaction to CSV");
        }
    }
    private static ArrayList<Transaction> loadTransactions() {
        //Pulling from the csvLine to use later in main
        ArrayList<Transaction> transactions = new ArrayList<>();
        try (BufferedReader buffReader = new BufferedReader(new FileReader("transactions.csv"))) {
            buffReader.readLine();
            String line;
            while ((line = buffReader.readLine()) != null) {
                Transaction transaction = fromCsvLine(line);
                transactions.add(transaction);
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions. Try again");
        }
        return transactions;
    }
}