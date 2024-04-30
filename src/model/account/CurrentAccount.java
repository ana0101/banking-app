package model.account;

import model.card.Card;

import java.util.HashSet;

public class CurrentAccount extends Account {
    private HashSet<Card> cards;

    public CurrentAccount(int userId, double balance) {
        super(userId, balance);
        this.cards = new HashSet<Card>();
    }

    public CurrentAccount(int accountId, double balance, String iban, int userId) {
        super(accountId, balance, iban, userId);
    }

    @Override
    public String toString() {
        return "Current Account:\n" +
                "  account id = " + accountId + "\n" +
                "  balance = " + balance + "\n" +
                "  iban = " + iban +  "\n";
    }
}
