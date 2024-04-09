package model.card;

import java.util.Calendar;
import java.util.Date;

public class CardTransaction implements Comparable<CardTransaction> {
    private static int transactionIdCount = 0;
    private int transactionId;
    private int cardId;
    private double amount;
    private Date date;
    private String description;

    public CardTransaction(int cardId, double amount, String description) {
        transactionIdCount ++;
        this.transactionId = transactionIdCount;
        this.cardId = cardId;
        this.amount = amount;
        this.date = Calendar.getInstance().getTime();  // current date
        this.description = description;
    }

    // used to sort card transactions in tree set by date
    @Override
    public int compareTo(CardTransaction cardTransaction) {
        return this.date.compareTo(cardTransaction.date);
    }

    @Override
    public String toString() {
        return "CardTransaction{" +
                "transactionId=" + transactionId +
                ", cardId=" + cardId +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
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
