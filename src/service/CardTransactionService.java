package service;

import model.card.CardTransaction;
import persistence.CardTransactionRepository;

import java.util.List;

public class CardTransactionService {
    public CardTransactionRepository cardTransactionRepository = new CardTransactionRepository();

    public List<CardTransaction> getCardTransactionsByCardId(int cardId) {
        return cardTransactionRepository.getCardTransactionsByCardId(cardId);
    }

    public int addCardTransaction(int cardId, double amount, String description) {
        CardTransaction cardTransaction = new CardTransaction(cardId, amount, description);
        return cardTransactionRepository.add(cardTransaction);
    }
}
