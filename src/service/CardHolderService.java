package service;

import exception.InvalidDataException;
import model.card.CardHolder;
import persistence.CardHolderRepository;

public class CardHolderService {
    public CardHolderRepository cardHolderRepository = new CardHolderRepository();

    public int addCardHolder(String firstName, String lastName, String cnp, int cardId) throws InvalidDataException {
        // to do: check if valid cardId
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new InvalidDataException("Invalid first name");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new InvalidDataException("Invalid last name");
        }
        if (cnp == null || cnp.trim().isEmpty()) {
            throw new InvalidDataException("Invalid cnp");
        }
        CardHolder cardHolder = new CardHolder(firstName, lastName, cnp, cardId);
        return cardHolderRepository.add(cardHolder);
    }
}
