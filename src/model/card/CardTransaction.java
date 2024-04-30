package model.card;

import java.util.Calendar;
import java.util.Date;

public class CardTransaction implements Comparable<CardTransaction> {
    private int transactionId;
    private double amount;
    private Date date;
    private String description;
    private int cardId;

    public CardTransaction(int cardId, double amount, String description) {
        this.cardId = cardId;
        this.amount = amount;
        this.date = Calendar.getInstance().getTime();  // current date
        this.description = description;
    }

    public CardTransaction(int transactionId, double amount, Date date, String description, int cardId) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.cardId = cardId;
    }

    // used to sort card transactions in tree set by date
    @Override
    public int compareTo(CardTransaction cardTransaction) {
        return this.date.compareTo(cardTransaction.date);
    }

    @Override
    public String toString() {
        return "Card Transaction:\n" +
                "  card id = " + cardId + "\n" +
                "  amount = " + amount + "\n" +
                "  date = " + date + "\n" +
                "  description = " + description + "\n";
    }

    public int getCardId() {
        return cardId;
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
