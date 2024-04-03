package model.card;

import java.util.Date;

public class CardTransaction implements Comparable<CardTransaction> {
    private static int transactionIdCount = 0;
    private int transactionId;
    private int cardId;
    private double amount;
    private Date date;
    private String description;

    public CardTransaction(int cardId, double amount, Date date, String description) {
        transactionIdCount ++;
        this.transactionId = transactionIdCount;
        this.cardId = cardId;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // used to sort card transactions in tree set by date
    @Override
    public int compareTo(CardTransaction cardTransaction) {
        return this.date.compareTo(cardTransaction.date);
    }
}
