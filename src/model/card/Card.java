package model.card;

import java.util.ArrayList;
import java.util.Date;

public class Card {
    private static int cardIdCount = 0;
    private int cardId;
    private int currentAccountId;
    private String cardNumber;
    private int cardHolderId;
    private Date expirationDate;
    private ArrayList<CardTransaction> cardTransactions;
}
