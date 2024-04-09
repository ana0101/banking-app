package persistence;

import model.card.Card;
import model.card.CardHolder;
import model.card.CardTransaction;

import java.util.ArrayList;
import java.util.TreeSet;

public class CardRepository implements GenericRepository<Card> {
    private ArrayList<Card> cards = new ArrayList<Card>();

    @Override
    public int add(Card card) {
        if (!cards.contains(card)) {
            cards.add(card);
            return card.getCardId();
        }
        return 0;
    }

    @Override
    public Card get(int id) {
        return cards.stream()
                .filter(card -> card.getCardId() == id)
                .findFirst()
                .orElse(null);
    }

    public TreeSet<CardTransaction> getCardTransactions(Card card) {
        return card.getCardTransactions();
    }

    public void addCardTransaction(Card card, CardTransaction cardTransaction) {
        card.addCardTransaction(cardTransaction);
    }

    @Override
    public void update(Card entity) {

    }

    public void setCardHolder(Card card, CardHolder cardHolder) {
        card.setCardHolder(cardHolder);
    }

    @Override
    public void delete(Card card) {
        cards.remove(card);
    }
}
