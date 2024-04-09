package persistence;

import model.card.CardTransaction;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CardTransactionRepository implements GenericRepository<CardTransaction> {
    private ArrayList<CardTransaction> cardTransactions = new ArrayList<>();

    @Override
    public int add(CardTransaction cardTransaction) {
        if (!cardTransactions.contains(cardTransaction)) {
            cardTransactions.add(cardTransaction);
            return cardTransaction.getTransactionId();
        }
        return 0;
    }

    @Override
    public CardTransaction get(int id) {
        return cardTransactions.stream()
                .filter(cardTransaction -> cardTransaction.getTransactionId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(CardTransaction entity) {

    }

    @Override
    public void delete(CardTransaction cardTransaction) {
        cardTransactions.remove(cardTransaction);
    }

    public void deleteByCardId(int cardId) {
        cardTransactions.removeIf(cardTransaction -> cardTransaction.getCardId() == cardId);
    }
}
