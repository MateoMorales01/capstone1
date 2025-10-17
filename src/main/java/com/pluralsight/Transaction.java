package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDate transactionDate;

    private LocalTime transactionTime;
    private double amount;
    private String descriptions;
    private String vendorName;

    public Transaction(LocalDate transactionDate, LocalTime transactionTime, String descriptions, String vendorName, double amount) {
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.amount = amount;
        this.descriptions = descriptions;
        this.vendorName = vendorName;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public LocalTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionDate=" + transactionDate +
                ", transactionTime=" + transactionTime +
                ", amount='" + amount + '\'' +
                ", descriptions='" + descriptions + '\'' +
                ", vendorName='" + vendorName + '\'' +
                '}';
    }

    public String display() {
        //Easy for showing Transactions
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return transactionDate.format(dateFormatter) + "|" + transactionTime.format(timeFormatter) + "|" + descriptions + "|" + vendorName + "|$" + amount;
    }


    public String writeTocsv() {
        //WritingTocsv is for writing out the transaction.
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return transactionDate.format(dateFormatter) + "|" + transactionTime.format(timeFormatter) + "|" + descriptions + "|" + vendorName + "|" + amount;
    }
}
