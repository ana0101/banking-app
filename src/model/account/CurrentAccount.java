package model.account;

import model.card.Card;

import java.util.HashSet;

public class CurrentAccount extends Account {
    private HashSet<Card> cards;

    public CurrentAccount(int userId, double balance, String iban) {
        super(userId, balance, iban);
        this.cards = new HashSet<Card>();
    }
}
