package model.card;

import java.util.*;

public class Card {
    private int cardId;
    private int currentAccountId;
    private String cardNumber;
    private Date expirationDate;
    private CardHolder cardHolder;
    private TreeSet<CardTransaction> cardTransactions;

    public Card(int currentAccountId) {
        this.currentAccountId = currentAccountId;
        this.cardNumber = generateCardNumber();
        this.expirationDate = generateExpirationDate();
        this.cardTransactions = new TreeSet<CardTransaction>();
    }

    public Card(int cardId, String cardNumber, Date expirationDate, int currentAccountId, CardHolder cardHolder) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.currentAccountId = currentAccountId;
        this.cardHolder = cardHolder;
    }

    private static String generateCardNumber() {
        String prefix = "4";

        Random random = new Random();
        StringBuilder builder = new StringBuilder(prefix);
        for (int i = 0; i < 14; i++) {
            builder.append(random.nextInt(10));
        }

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

        return cardNumberWithoutChecksum + checksum;
    }


    private static Date generateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 3);
        Date expirationDate = calendar.getTime();
        return expirationDate;
    }

    @Override
    public String toString() {
        return "Card:\n" +
                "  card id = " + cardId + "\n" +
                "  card number = " + cardNumber + "\n" +
                "  expiration date = " + expirationDate + "\n" +
                " " + cardHolder.toString();
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

    public int getCardId() {
        return cardId;
    }

    public int getCurrentAccountId() {
        return currentAccountId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}
