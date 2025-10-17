package com.pluralsight;

import java.io.*;           // For file reading/writing
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.YearMonth;     // For monthly date ranges
import java.util.ArrayList;
import java.util.Scanner;

public class LedgerApp {

    public static void main(String[] args) throws IOException {
        String fileName = "transactions.csv";       // CSV file where all transactions are stored
        Scanner scanner = new Scanner(System.in);     // For reading user input from console

        // Infinite loop - keeps showing the home menu until user exits
        while (true) {
            menuScreen();     //Display home screen
            String choice = scanner.nextLine().toUpperCase();   //Get user choice, uppercase for consistency

            switch (choice) {
                case "D":
                    //Add a deposit transaction
                    addDeposit(scanner);
                    break;
                case "P":
                    // Add a payment (a negative amount)
                    addPayment(scanner);
                    break;
                case "L":
                    // Open the Ledger sub-menu
                    boolean ledgerRunning = true;
                    while (ledgerRunning) {
                        System.out.println("===============================================================");
                    System.out.println("A) All Entries");
                    System.out.println("D) Deposits Only");
                    System.out.println("P) Payments Only");
                    System.out.println("R) Reports");
                    System.out.println("H) Home");
                    System.out.println("X) Exit Application");
                        System.out.println("===============================================================");
                    System.out.print("\nChoose an option: ");
                    String userLedgerChoice = scanner.nextLine().trim().toUpperCase();

                        switch (userLedgerChoice) {
                            case "A":
                                // Show all transactions in the file
                                commitAllEntries();
                                break;
                            case "D":
                                // Show only deposits (positive amounts)
                                addingDeposit();
                                break;
                            case "P":
                                // Show only payments (negative amounts)
                                makePayments();
                                break;
                            case "R":
                                // Open the reports sub-menu
                                boolean reportsRunning = true;
                                while (reportsRunning) {
                                    System.out.println("===============================================================");
                                    System.out.println("1) Month To Date");
                                    System.out.println("2) Previous Month");
                                    System.out.println("3) Year To Date");
                                    System.out.println("4) Previous Year");
                                    System.out.println("5) Search by Vendor");
                                    System.out.println("0) Back");
                                    System.out.println("===============================================================");

                                    //Input handling with validation
                                    String input = scanner.nextLine();
                                    int userReportChoice;

                                    try {
                                        userReportChoice = Integer.parseInt(input);
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please enter a number.");
                                        continue; // goes back to the top of the loop
                                    }


                                    //Run selected report
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
                                            reportsRunning = false; // Go back to ledger menu
                                            break;
                                        default:
                                            System.out.println("Invalid report choice. Please try again.");
                                    }
                                }
                                break;
                            case "H":
                                // Go back to home screen
                                ledgerRunning = false;
                                break;
                            case "X":
                                // Exit application entirely
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

    // ======== REPORT METHODS ========

    // Displays all transactions from a specific vendor name
    private static void showVendor(Scanner scanner) {
        System.out.println("===============================================================");
        System.out.print("                     Enter Vendor Name to Search: ");
        System.out.println("===============================================================");
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
    }

    // Shows all transactions that occurred during the previous calendar year
    private static void showPreviousYear () {
                Year previousYear = Year.now().minusYears(1);
                LocalDate startOfPreviousYear = previousYear.atDay(1);
                LocalDate endOfPreviousYear = previousYear.atDay(previousYear.length());
                ArrayList<Transaction> yearToDateTransactions = loadTransactions();

        System.out.println("===============================================================");
                System.out.println("            --- PREVIOUS YEAR REPORT ---");
                System.out.println(" Period: " + startOfPreviousYear + " to " + endOfPreviousYear);
        System.out.println("===============================================================");

                for (Transaction item : yearToDateTransactions) {
                    if (item != null) {
                        if (item.getTransactionDate().isAfter(startOfPreviousYear.minusDays(1)) &&
                                item.getTransactionDate().isBefore(endOfPreviousYear.plusDays(1))) {
                            System.out.println(item.display());
                        }
                    }
                }
            }

            // Shows transactions from the start of the current year up to today
            private static void showYearToDate () {
                LocalDate today = LocalDate.now();
                LocalDate startOfYear = today.withDayOfYear(1);

                System.out.println("===============================================================");
                System.out.println("                 --- YEAR TO DATE REPORT ---");
                System.out.println("           Period: " + startOfYear + " to " + today);
                System.out.println("===============================================================");
                ArrayList<Transaction> yearToDateTransactions = loadTransactions();

                for (Transaction item : yearToDateTransactions) {
                    if (item != null) {
                        if (item.getTransactionDate().isAfter(startOfYear.minusDays(1)) &&
                                item.getTransactionDate().isBefore(today.plusDays(1))) {
                            System.out.println(item.display());
                        }
                    }
                }
            }

            // Shows transactions for the previous calendar month
            private static void showingPreviousMonth () {
                YearMonth previousYearMonth = YearMonth.now().minusMonths(1);
                LocalDate startOfPreviousMonth = previousYearMonth.atDay(1);
                LocalDate endOfPreviousMonth = previousYearMonth.atEndOfMonth();

                System.out.println("===============================================================");
                System.out.println("                   --- PREVIOUS MONTH REPORT ---");
                System.out.println("Period: " + startOfPreviousMonth + " to " + endOfPreviousMonth);
                System.out.println("===============================================================");
                ArrayList<Transaction> prevMonthTransactions = loadTransactions();

                for (Transaction item : prevMonthTransactions) {
                    if (item != null) {
                        if (item.getTransactionDate().isAfter(startOfPreviousMonth.minusDays(1)) &&
                                item.getTransactionDate().isBefore(endOfPreviousMonth.plusDays(1))) {
                            System.out.println(item.display());
                        }
                    }
                }
            }

            // Shows all transactions for the current month up to today
            private static void addingMonthToDate () {
                LocalDate now = LocalDate.now();
                LocalDate startOfMonth = now.withDayOfMonth(1);

                System.out.println("===============================================================");
                System.out.println("                    --- MONTH TO DATE REPORT ---");
                System.out.println("                Period: " + startOfMonth + " to " + now);
                System.out.println("===============================================================");
                ArrayList<Transaction> transactions = loadTransactions();
                for (Transaction item : transactions) {
                    if (item.getTransactionDate().isAfter(startOfMonth) && item.getTransactionDate().isBefore(now)) {
                        System.out.println(item.display());
                    }
                }
            }

            // ======== LEDGER DISPLAY METHODS ========

            //Displays all payment (negative) transactions
            private static void makePayments () {
                System.out.println("===============================================================");
                System.out.println("                          Payments only");
                System.out.println("===============================================================");
                ArrayList<Transaction> transactions = loadTransactions();
                for (Transaction item : transactions) {
                    if (item != null) {
                        if (item.getAmount() < 0) {
                            System.out.println(item.display());
                        }
                    }
                }
            }

            // Displays only deposit (positive) transactions
            private static void addingDeposit () {
                System.out.println("===============================================================");
                System.out.println("                          Deposits only");
                System.out.println("===============================================================");
                ArrayList<Transaction> transactions = loadTransactions();
                for (Transaction item : transactions) {
                    if (item != null) {
                        if (item.getAmount() > 0) {
                            System.out.println(item.display());
                        }
                    }
                }
            }

            // Displays every transaction from the CSV file
            private static void commitAllEntries () {
                System.out.println("===============================================================");
                System.out.println("                     Printing all Entries");
                System.out.println("===============================================================");
                ArrayList<Transaction> transactions = loadTransactions();
                for (Transaction item : transactions) {
                    if (item != null) {
                        System.out.println(item.display());
                    }
                }
            }

            // ======== ADDING NEW TRANSACTIONS ========

            // Adds a payment (negative amount) to the CSV
            private static void addPayment (Scanner scanner){
                try {
                    System.out.println("===============================================================");
                    System.out.println("                   Enter description: ");
                    System.out.println("===============================================================");
                    String description = scanner.nextLine();

                    System.out.println("===============================================================");
                    System.out.println("                       Enter Vendor: ");
                    System.out.println("===============================================================");
                    String vendor = scanner.nextLine();

                    System.out.println("===============================================================");
                    System.out.println("                      Enter amount: ");
                    System.out.println("===============================================================");
                    double amount = Double.parseDouble(scanner.nextLine());

                    // Create Transaction object (negative amount for payment)
                    Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, -amount);
                    saveTransaction(transaction);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }

            //Adds a deposit (positive amount to the CSV
            private static void addDeposit (Scanner scanner){
                try {
                    System.out.println("===============================================================");
                    System.out.println("                     Enter description: ");
                    System.out.println("===============================================================");
                    String description = scanner.nextLine();

                    System.out.println("===============================================================");
                    System.out.println("                        Enter Vendor: ");
                    System.out.println("===============================================================");
                    String vendor = scanner.nextLine();

                    System.out.println("===============================================================");
                    System.out.println("                       Enter amount: ");
                    System.out.println("===============================================================");
                    double amount = Double.parseDouble(scanner.nextLine());

                    //Create Transaction object (positive amount for deposit)
                    Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
                    saveTransaction(transaction);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }

            // ======== MENU DISPLAY ========

            // Displays main menu options
            static void menuScreen () {
                System.out.println("                                  $     ");
                System.out.println("                                $$$$$$  ");
                System.out.println("                                $ $    ");
                System.out.println("                                 $$$$$  ");
                System.out.println("                                  $  $ ");
                System.out.println("                                  $  $ ");
                System.out.println("                                $$$$$$  ");
                System.out.println("                                  $     ");
                System.out.println("    💰💰💰  W E L C O M E   T O   M O N E Y   A P P  💰💰💰");
                System.out.println("===============================================================");
                System.out.println("D) Add Deposit");
                System.out.println("P) Make Payment (Debit)");
                System.out.println("L) Ledger");
                System.out.println("X) Exit");
                System.out.println("===============================================================");
                System.out.println("Enter Choice: ");
            }

            // Displays ledger menu options
            private void ledgerMenu () {

                while (true) {
                    System.out.println("===============================================================");
                    System.out.println("                         === Ledger Menu ===");
                    System.out.println("(T) All Transactions");
                    System.out.println("(D) Deposits");
                    System.out.println("(P) Payments");
                    System.out.println("(R) Reports");
                    System.out.println("(X) Back to Main Menu");
                    System.out.println("===============================================================");
                    System.out.println("Choose an option: ");
                    Scanner scanner = null;
                    String choice = scanner.nextLine().toUpperCase();

                    switch (choice) {

                    }


                }

            }

            // ======== FILE HANDLING ========


            // Converts a single CSV line into a Transaction object
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

            // Saves a new transaction to the CSV file
            private static void saveTransaction (Transaction transaction){
                //Updating Date/Time formatter
                try (BufferedWriter buffWriter = new BufferedWriter(new FileWriter("transactions.csv", true))) {
                    buffWriter.write("\n" + transaction.writeTocsv());
                } catch (IOException e) {
                    System.out.println("Error saving transaction to CSV");
                }
            }

            // Loads all transactions from the CSV file into a list
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