package model.account;

import model.card.Card;

import java.util.ArrayList;

public class CurrentAccount extends Account {
    private ArrayList<Card> cards;

    public CurrentAccount(int userId, double balance, String iban) {
        super(userId, balance, iban);
        this.cards = new ArrayList<Card>();
    }
}
