package model.card;

import java.util.Date;
import java.util.Objects;
import java.util.TreeSet;

public class Card {
    private static int cardIdCount = 0;
    private int cardId;
    private int currentAccountId;
    private String cardNumber;
    private Date expirationDate;
    private CardHolder cardHolder;
    private TreeSet<CardTransaction> cardTransactions;

    public Card(int currentAccountId, String cardNumber, CardHolder cardHolder, Date expirationDate) {
        this.currentAccountId = currentAccountId;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cardHolder = cardHolder;
        this.cardTransactions = new TreeSet<CardTransaction>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardId == card.cardId && currentAccountId == card.currentAccountId && Objects.equals(cardNumber, card.cardNumber) && Objects.equals(expirationDate, card.expirationDate) && Objects.equals(cardTransactions, card.cardTransactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, currentAccountId, cardNumber, expirationDate, cardTransactions);
    }
}
