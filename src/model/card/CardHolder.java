package model.card;

public class CardHolder {
    private static int cardHolderIdCount = 0;
    private int cardHolderId;
    private String firstName;
    private String lastName;
    private String cnp;
    private int cardId;

    public CardHolder(String firstName, String lastName, String cnp, int cardId) {
        cardHolderIdCount ++;
        this.cardHolderId = cardHolderIdCount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.cardId = cardId;
    }
}
