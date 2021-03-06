package com.expensemanager.expensemanager.models;

import java.io.Serializable;

public class TransactionModel implements Serializable {

    private  int txnID;

    private  String transactionType;
    private  String date;
    private  double amount;
    private  String description;
    private  String category;
    private  byte[] refImage;
    private  String status;
    

    public String getTransactionType() {//alt+ins for using getter and setter
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getRefImage() {
        return refImage;
    }

    public void setRefImage(byte[] refImage) {
        this.refImage = refImage;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;

    }

    public int getTxnID() {
        return txnID;
    }

    public void setTxnID(int txnID) {
        this.txnID = txnID;
    }
}
