package model.card;

import java.util.*;

public class Card {
    private static int cardIdCount = 0;
    private int cardId;
    private int currentAccountId;
    private String cardNumber;
    private Date expirationDate;
    private CardHolder cardHolder;
    private TreeSet<CardTransaction> cardTransactions;

    public Card(int currentAccountId, CardHolder cardHolder) {
        cardIdCount ++;
        this.cardId = cardIdCount;
        this.currentAccountId = currentAccountId;
        this.cardNumber = generateCardNumber();
        this.expirationDate = generateExpirationDate();
        this.cardHolder = cardHolder;
        this.cardTransactions = new TreeSet<CardTransaction>();
    }

    public Card(int currentAccountId) {
        cardIdCount ++;
        this.cardId = cardIdCount;
        this.currentAccountId = currentAccountId;
        this.cardNumber = generateCardNumber();
        this.expirationDate = generateExpirationDate();
        this.cardTransactions = new TreeSet<CardTransaction>();
    }

    private static String generateCardNumber() {
        // Card number prefix (you can change this based on the issuer)
        String prefix = "4"; // This is a common prefix for Visa cards

        // Generate random 15 digits
        Random random = new Random();
        StringBuilder builder = new StringBuilder(prefix);
        for (int i = 0; i < 15; i++) {
            builder.append(random.nextInt(10));
        }

        // Calculate the checksum using Luhn algorithm
        String cardNumberWithoutChecksum = builder.toString();
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumberWithoutChecksum.length() - 1; i >= 0; i--) {
            int digit = Integer.parseInt(cardNumberWithoutChecksum.substring(i, i + 1));
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        int checksum = (sum * 9) % 10;

        // Append the checksum to the card number
        return cardNumberWithoutChecksum + checksum;
    }

    private static Date generateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Set the expiration date to be three years from the current date
        calendar.add(Calendar.YEAR, 3);
        Date expirationDate = calendar.getTime();
        return expirationDate;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", currentAccountId=" + currentAccountId +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate=" + expirationDate +
                ", cardHolder=" + cardHolder.toString() +
                ", cardTransactions=" + cardTransactions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardId == card.cardId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, currentAccountId, cardNumber, expirationDate);
    }

    public static int getCardIdCount() {
        return cardIdCount;
    }

    public static void setCardIdCount(int cardIdCount) {
        Card.cardIdCount = cardIdCount;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(int currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public CardHolder getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(CardHolder cardHolder) {
        this.cardHolder = cardHolder;
    }

    public TreeSet<CardTransaction> getCardTransactions() {
        return cardTransactions;
    }

    public void setCardTransactions(TreeSet<CardTransaction> cardTransactions) {
        this.cardTransactions = cardTransactions;
    }

    public void addCardTransaction(CardTransaction cardTransaction) {
        this.cardTransactions.add(cardTransaction);
    }
}
