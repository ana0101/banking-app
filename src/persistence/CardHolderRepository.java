package persistence;

import model.card.CardHolder;

import java.util.ArrayList;

public class CardHolderRepository implements GenericRepository<CardHolder> {
    private ArrayList<CardHolder> cardHolders = new ArrayList<>();

    @Override
    public int add(CardHolder cardHolder) {
        if (!cardHolders.contains(cardHolder)) {
            cardHolders.add(cardHolder);
            return cardHolder.getCardHolderId();
        }
        return 0;
    }

    @Override
    public CardHolder get(int id) {
        return cardHolders.stream()
                .filter(cardHolder -> cardHolder.getCardHolderId() ==  id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(CardHolder entity) {

    }

    @Override
    public void delete(CardHolder cardHolder) {
        cardHolders.remove(cardHolder);
    }
}
