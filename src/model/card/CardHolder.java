package model.card;

public class CardHolder {
    private int cardHolderId;
    private String firstName;
    private String lastName;
    private String cnp;
    private int cardId;

    public CardHolder(String firstName, String lastName, String cnp, int cardId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.cardId = cardId;
    }

    public CardHolder(int cardHolderId, String firstName, String lastName, String cnp, int cardId) {
        this.cardHolderId = cardHolderId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.cardId = cardId;
    }

    @Override
    public String toString() {
        return "Card Holder:\n" +
                "  first name = " + firstName + "\n" +
                "  last name = " + lastName + "\n" +
                "  cnp = " + cnp + "\n";
    }

    public int getCardId() {
        return cardId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCnp() {
        return cnp;
    }
}
