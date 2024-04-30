package service;

import exception.InvalidDataException;
import model.card.Card;
import persistence.CardRepository;

import java.util.List;

public class CardService {
    public CardRepository cardRepository = new CardRepository();

    public Card getCard(int cardId) {
        return cardRepository.get(cardId);
    }

    public List<Card> getCardsByCurrentAccountId(int currentAccountId) {
        return cardRepository.getCardsByCurrentAccountId(currentAccountId);
    }

    public int addCard(int currentAccountId) throws InvalidDataException {
        Card card = new Card(currentAccountId);
        return cardRepository.add(card);
    }

    public void deleteCard(int cardId) throws InvalidDataException {
        Card card = getCard(cardId);
        if (card == null) {
            throw new InvalidDataException("Invalid card id");
        }
        cardRepository.delete(card);
    }
}
