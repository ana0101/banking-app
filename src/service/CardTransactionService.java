package service;

import exception.InvalidDataException;
import model.card.CardTransaction;
import persistence.CardTransactionRepository;

import java.util.Date;

public class CardTransactionService {
    public CardTransactionRepository cardTransactionRepository = new CardTransactionRepository();

    public int addCardTransaction(int cardId, double amount, String description) {
        // to do: check if card id valid
        CardTransaction cardTransaction = new CardTransaction(cardId, amount, description);
        return cardTransactionRepository.add(cardTransaction);
    }

    public CardTransaction getCardTransaction(int cardTransactionId) {
        return cardTransactionRepository.get(cardTransactionId);
    }

    public void deleteCardTransaction(int cardTransactionId) throws InvalidDataException {
        CardTransaction cardTransaction = getCardTransaction(cardTransactionId);
        if (cardTransaction == null) {
            throw new InvalidDataException("Invalid card transaction id");
        }
        cardTransactionRepository.delete(cardTransaction);
    }

    public void deleteCardTransactionsByCardId(int cardId) {
        cardTransactionRepository.deleteByCardId(cardId);
    }
}
