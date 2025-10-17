package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.YearMonth;
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
                    boolean ledgerRunning = true;
                    while (ledgerRunning) {
                    System.out.println("A) All Entries");
                    System.out.println("D) Deposits Only");
                    System.out.println("P) Payments Only");
                    System.out.println("R) Reports");
                    System.out.println("H) Home");
                    System.out.println("X) Exit Application");
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
                                boolean reportsRunning = true;
                                while (reportsRunning) {
                                    System.out.println("1) Month To Date");
                                    System.out.println("2) Previous Month");
                                    System.out.println("3) Year To Date");
                                    System.out.println("4) Previous Year");
                                    System.out.println("5) Search by Vendor");
                                    System.out.println("0) Back");

                                    String input = scanner.nextLine();
                                    int userReportChoice;

                                    try {
                                        userReportChoice = Integer.parseInt(input);
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a number.");
                                        continue; // goes back to the top of the loop
                                    }



                                    switch (userReportChoice) {
                                        case 1:
                                            addingMonthToDate();
                                            break;
                                        case 2:
                                            showingPreviousMonth();
                                            break;
                                        case 3:
                                            showYearToDate();
                                            break;
                                        case 4:
                                            showPreviousYear();
                                            break;
                                        case 5:
                                            showVendor(scanner);
                                            break;
                                        case 0:
                                            reportsRunning = false;
                                            break;
                                        default:
                                            System.out.println("Invalid report choice. Please try again.");
                                    }
                                }
                                break;
                            case "H":
                                ledgerRunning = false;
                                break;
                            case "X":
                                System.out.println("Exiting application...Goodbye!");
                                scanner.close();
                                return;
                            default:
                                System.out.println("Invalid choice. Please try again.");

                        }
                    }
                    break;
            }

            }
        }

    private static void showVendor(Scanner scanner) {
        System.out.print("\nEnter Vendor Name to Search: ");
        String searchVendor = scanner.nextLine().trim();
        ArrayList<Transaction> vendor = loadTransactions();
        System.out.println("\n--- VENDOR REPORT: " + searchVendor + " ---");

        boolean found = false;
        for (Transaction item : vendor) {
            if (item != null && item.getVendorName().equalsIgnoreCase(searchVendor)) {
                System.out.println(item.display());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No transactions found for vendor: " + searchVendor);
        }
        return;
    }

    private static void showPreviousYear () {
                Year previousYear = Year.now().minusYears(1);
                LocalDate startOfPreviousYear = previousYear.atDay(1);
                LocalDate endOfPreviousYear = previousYear.atDay(previousYear.length());
                ArrayList<Transaction> yearToDateTransactions = loadTransactions();

                System.out.println("\n--- PREVIOUS YEAR REPORT ---");
                System.out.println("Period: " + startOfPreviousYear + " to " + endOfPreviousYear);

                for (Transaction item : yearToDateTransactions) {
                    if (item != null) {
                        if (item.getTransactionDate().isAfter(startOfPreviousYear.minusDays(1)) &&
                                item.getTransactionDate().isBefore(endOfPreviousYear.plusDays(1))) {
                            System.out.println(item.display());
                        }
                    }
                }
                return;
            }

            private static void showYearToDate () {
                LocalDate today = LocalDate.now();
                LocalDate startOfYear = today.withDayOfYear(1);

                System.out.println("\n--- YEAR TO DATE REPORT ---");
                System.out.println("Period: " + startOfYear + " to " + today);
                ArrayList<Transaction> yearToDateTransactions = loadTransactions();

                for (Transaction item : yearToDateTransactions) {
                    if (item != null) {
                        if (item.getTransactionDate().isAfter(startOfYear.minusDays(1)) &&
                                item.getTransactionDate().isBefore(today.plusDays(1))) {
                            System.out.println(item.display());
                        }
                    }
                }
                return;
            }

            private static void showingPreviousMonth () {
                YearMonth previousYearMonth = YearMonth.now().minusMonths(1);
                LocalDate startOfPreviousMonth = previousYearMonth.atDay(1);
                LocalDate endOfPreviousMonth = previousYearMonth.atEndOfMonth();

                System.out.println("\n--- PREVIOUS MONTH REPORT ---");
                System.out.println("Period: " + startOfPreviousMonth + " to " + endOfPreviousMonth);
                ArrayList<Transaction> prevMonthTransactions = loadTransactions();

                for (Transaction item : prevMonthTransactions) {
                    if (item != null) {
                        if (item.getTransactionDate().isAfter(startOfPreviousMonth.minusDays(1)) &&
                                item.getTransactionDate().isBefore(endOfPreviousMonth.plusDays(1))) {
                            System.out.println(item.display());
                        }
                    }
                }
                return;
            }

            private static void addingMonthToDate () {
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
                return;
            }

            private static void makePayments () {
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

            private static void addingDeposit () {
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

            private static void commitAllEntries () {
                System.out.println("Printing all Entries");
                ArrayList<Transaction> transactions = loadTransactions();
                for (Transaction item : transactions) {
                    if (item != null) {
                        System.out.println(item.display());
                    }
                }
            }

            private static void addPayment (Scanner scanner){
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

            private static void addDeposit (Scanner scanner){
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

            static void menuScreen () {
                System.out.println("--- Welcome to the Financial Ledger App ---");
                System.out.println("\n--- Home Screen ---");
                System.out.println("D) Add Deposit");
                System.out.println("P) Make Payment (Debit)");
                System.out.println("L) Ledger");
                System.out.println("X) Exit");
                System.out.println("Enter Choice: ");
            }

            //

            private void ledgerMenu () {

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

            public static Transaction fromCsvLine (String line){
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
            private static void saveTransaction (Transaction transaction){
                //Updating Date/Time formatter
                try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter("transactions.csv", true))) {
                    buffWriter.write("\n" + transaction.writeTocsv());
                } catch (IOException e) {
                    System.out.println("Error saving transaction to CSV");
                }
            }
            private static ArrayList<Transaction> loadTransactions () {
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