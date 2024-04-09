package service;

import exception.InvalidDataException;
import model.card.Card;
import model.card.CardHolder;
import model.card.CardTransaction;
import persistence.CardRepository;

import java.util.TreeSet;

public class CardService {
    public CardRepository cardRepository = new CardRepository();

    public int addCard(int currentAccountId) throws InvalidDataException {
        // to do: check if current account id valid
        Card card = new Card(currentAccountId);
        return cardRepository.add(card);
    }

    public Card getCard(int cardId) {
        return cardRepository.get(cardId);
    }

    public TreeSet<CardTransaction> getCardTransactions(int cardId) throws InvalidDataException {
        Card card = getCard(cardId);
        if (card == null) {
            throw new InvalidDataException("Invalid card id");
        }
        return cardRepository.getCardTransactions(card);
    }

    public void addCardTransaction(int cardId, CardTransaction cardTransaction) throws InvalidDataException {
        Card card = getCard(cardId);
        if (card == null) {
            throw new InvalidDataException("Invalid card id");
        }
        cardRepository.addCardTransaction(card, cardTransaction);
    }

    public void setCardHolder(int cardId, CardHolder cardHolder) throws InvalidDataException {
        Card card = getCard(cardId);
        if (card == null) {
            throw new InvalidDataException("Invalid card id");
        }
        cardRepository.setCardHolder(card, cardHolder);
    }

    public void deleteCard(int cardId) throws InvalidDataException {
        Card card = getCard(cardId);
        if (card == null) {
            throw new InvalidDataException("Invalid card id");
        }
        cardRepository.delete(card);
    }
}
