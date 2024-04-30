package model.transfer;

import java.util.Calendar;
import java.util.Date;

public class Transfer {
    private int transferId;
    private double amount;
    private Date date;
    private String description;
    private int payerAccountId;
    private int recipientAccountId;

    public Transfer(int payerAccountId, int recipientAccountId, double amount, String description) {
        this.payerAccountId = payerAccountId;
        this.recipientAccountId = recipientAccountId;
        this.amount = amount;
        this.date = Calendar.getInstance().getTime();  // current date
        this.description = description;
    }

    public Transfer(int transferId, double amount, Date date, String description, int payerAccountId, int recipientAccountId) {
        this.transferId = transferId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.payerAccountId = payerAccountId;
        this.recipientAccountId = recipientAccountId;
    }

    @Override
    public String toString() {
        return "Transfer:\n" +
                "  payer account id = " + payerAccountId + "\n" +
                "  recipient account id = " + recipientAccountId + "\n" +
                "  amount = " + amount + "\n" +
                "  date = " + date + "\n" +
                "  description = " + description + "\n";
    }

    public int getPayerAccountId() {
        return payerAccountId;
    }

    public int getRecipientAccountId() {
        return recipientAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
