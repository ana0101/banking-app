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

    public CardHolder(int cardId) {
        cardHolderIdCount ++;
        this.cardHolderId = cardHolderIdCount;
        this.cardId = cardId;
    }

    @Override
    public String toString() {
        return "CardHolder{" +
                "cardHolderId=" + cardHolderId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cnp='" + cnp + '\'' +
                ", cardId=" + cardId +
                '}';
    }

    public int getCardHolderId() {
        return cardHolderId;
    }

    public void setCardHolderId(int cardHolderId) {
        this.cardHolderId = cardHolderId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }
}
