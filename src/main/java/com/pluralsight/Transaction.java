package com.pluralsight;

public class Transaction {

    private double transactionDate;
    private String itemType;
    private String descriptions;
    private String vendorName;

    public double getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(double transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
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

    @Override
    public String toString() {
        return "Application{" +
                "transactionDate=" + transactionDate +
                ", itemType='" + itemType + '\'' +
                ", descriptions='" + descriptions + '\'' +
                ", vendorName='" + vendorName + '\'' +
                '}';
    }
}
