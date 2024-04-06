package model.account;

import model.card.Card;

import java.util.HashSet;

public class CurrentAccount extends Account {
    private HashSet<Card> cards;

    public CurrentAccount(int userId, double balance) {
        super(userId, balance);
        this.cards = new HashSet<Card>();
    }

    @Override
    public String toString() {
        return "CurrentAccount{" +
                "cards=" + cards +
                ", accountId=" + accountId +
                ", userId=" + userId +
                ", balance=" + balance +
                ", iban='" + iban + '\'' +
                '}';
    }

    public HashSet<Card> getCards() {
        return cards;
    }

    public void setCards(HashSet<Card> cards) {
        this.cards = cards;
    }
}
