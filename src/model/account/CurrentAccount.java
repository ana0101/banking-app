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

    public Card getCard(int cardId) {
        for (Card card : cards) {
            if (card.getCardId() == cardId) {
                return card;
            }
        }
        return null;
    }

    public HashSet<Card> getCards() {
        return cards;
    }

    public void setCards(HashSet<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void deleteCard(Card card) {
        this.cards.remove(card);
    }
}
