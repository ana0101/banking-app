package model.transfer;

import java.util.Calendar;
import java.util.Date;

public class Transfer {
    private static int transferIdCount = 0;
    private int transferId;
    private int payerAccountId;
    private int recipientAccountId;
    private double amount;
    private Date date;
    private String description;

    public Transfer(int payerAccountId, int recipientAccountId, double amount, String description) {
        transferIdCount ++;
        this.transferId = transferIdCount;
        this.payerAccountId = payerAccountId;
        this.recipientAccountId = recipientAccountId;
        this.amount = amount;
        this.date = Calendar.getInstance().getTime();  // current date
        this.description = description;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", payerAccountId=" + payerAccountId +
                ", recipientAccountId=" + recipientAccountId +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getPayerAccountId() {
        return payerAccountId;
    }

    public void setPayerAccountId(int payerAccountId) {
        this.payerAccountId = payerAccountId;
    }

    public int getRecipientAccountId() {
        return recipientAccountId;
    }

    public void setRecipientAccountId(int recipientAccountId) {
        this.recipientAccountId = recipientAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
