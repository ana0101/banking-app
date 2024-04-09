package view;

import exception.InvalidDataException;
import model.account.Account;
import model.account.CurrentAccount;
import model.card.Card;
import model.card.CardTransaction;
import model.transfer.Transfer;
import model.user.User;
import service.*;

import java.util.Scanner;

public class ConsoleApp {
    private Scanner scanner = new Scanner(System.in);
    private UserService userService = new UserService();
    private AccountService accountService = new AccountService();
    private CardService cardService = new CardService();
    private CardHolderService cardHolderService = new CardHolderService();
    private CardTransactionService cardTransactionService = new CardTransactionService();
    private TransferService transferService = new TransferService();

    public static void main(String args[]) throws InvalidDataException {
        ConsoleApp app = new ConsoleApp();
        app.addData();
        app.showUserMenu();
    }

    private void showUserMenu() throws InvalidDataException {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.println("4. See users");
        int option = readOption(1, 4);
        executeUserOption(option);
    }

    private void showAccountMenu(int userId) throws InvalidDataException {
        System.out.println("1. View accounts");
        System.out.println("2. Manage current account");
        System.out.println("3. Manage deposit accounts");
        System.out.println("4. Back");
        int option = readOption(1, 4);
        executeAccountOption(option, userId);
    }

    private void showCurrentAccountMenu(int userId, boolean hasCurrentAccount) throws InvalidDataException {
        int option;
        if (hasCurrentAccount) {
            System.out.println("1. View current account");
            System.out.println("3. Manage cards");
            System.out.println("4. Manage transfers");
            System.out.println("5. Back");
            option = readOption2(new int[]{1, 3, 4, 5});
        }
        else {
            System.out.println("2. Add current account");
            System.out.println("5. Back");
            option = readOption2(new int[]{2, 5});
        }
        executeCurrentAccountOption(option, userId);
    }

    private void showCardMenu(int userId, int currentAccountId) throws InvalidDataException {
        System.out.println("1. View cards");
        System.out.println("2. Add card");
        System.out.println("3. Delete card");
        System.out.println("4. Manage card transactions");
        System.out.println("5. Back");
        int option = readOption(1, 5);
        executeCardOption(option, userId, currentAccountId);
    }

    private void showTransactionMenu(int userId, int currentAccountId, int cardId) throws InvalidDataException {
        System.out.println("1. View transactions");
        System.out.println("2. Make a card transaction");
        System.out.println("3. Back");
        int option = readOption(1, 3);
        executeTransactionOption(option, userId, currentAccountId, cardId);
    }

    private void showTransferMenu(int userId, int currentAccountId) throws InvalidDataException {
        System.out.println("1. View transfers");
        System.out.println("2. Make a transfer");
        System.out.println("3. Back");
        int option = readOption(1, 3);
        executeTransferMenu(option, userId, currentAccountId);
    }

    private void executeUserOption(int option) throws InvalidDataException {
        switch (option) {
            case 1:
                // login
                int userId = 0;
                try {
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    userId = userService.loginUser(username, password).getUserId();
                    showAccountMenu(userId);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    showUserMenu();
                }
                break;
            case 2:
                // register
                try {
                    System.out.println("Enter first name: ");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter last name: ");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter cnp: ");
                    String cnp = scanner.nextLine();
                    System.out.println("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    userService.registerUser(firstName, lastName, cnp, username, password);
                    showUserMenu();
                }
                catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
                    showUserMenu();
                }
                break;
            case 3:
                // exit
                System.exit(0);
            case 4:
                for (User user : userService.getAllUsers()) {
                    System.out.println(user);
                }
                showUserMenu();
                break;
        }
    }

    private void executeAccountOption(int option, int userId) throws InvalidDataException {
        switch (option) {
            case 1:
                // see accounts
                for (Account account : accountService.getUserAccounts(userId)) {
                    System.out.println(account);
                }
                showAccountMenu(userId);
                break;
            case 2:
                // manage current account
                boolean hasCurrentAccount = accountService.getUserAccounts(userId).size() > 0;
                showCurrentAccountMenu(userId, hasCurrentAccount);
                break;
            case 3:
                // manage deposit accounts
                break;
            case 4:
                // back
                showUserMenu();
                break;
        }
    }

    private void executeCurrentAccountOption(int option, int userId) throws InvalidDataException {
        switch (option) {
            case 1:
                // view current account
                System.out.println(accountService.getUserCurrentAccount(userId));
                showCurrentAccountMenu(userId, true);
                break;
            case 2:
                // add current account
                try {
                    System.out.println("Enter balance: ");
                    double balance = readDouble();
                    accountService.addCurrentAccount(userId, balance);
                    showCurrentAccountMenu(userId, true);
                }
                catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
                    showCurrentAccountMenu(userId, false);
                }
                break;
            case 3:
                // manage cards
                showCardMenu(userId, accountService.getUserCurrentAccount(userId).getAccountId());
                break;
            case 4:
                // manage transfers
                showTransferMenu(userId, accountService.getUserCurrentAccount(userId).getAccountId());
                break;
            case 5:
                // back
                showAccountMenu(userId);
                break;
        }
    }

    private void executeCardOption(int option, int userId, int currentAccountId) throws InvalidDataException {
        switch (option) {
            case 1:
                // view cards
                for (Card card : accountService.getCards(currentAccountId)) {
                    System.out.println(card);
                }
                showCardMenu(userId, currentAccountId);
                break;
            case 2:
                // add card
                try {
                    System.out.println("Enter holder first name: ");
                    String holderFirstName = scanner.nextLine();
                    System.out.println("Enter holder last name: ");
                    String holderLastName = scanner.nextLine();
                    System.out.println("Enter holder cnp: ");
                    String holderCnp = scanner.nextLine();
                    int cardId = cardService.addCard(currentAccountId);
                    int cardHolderId = cardHolderService.addCardHolder(holderFirstName, holderLastName, holderCnp, cardId);
                    cardService.setCardHolder(cardId, cardHolderService.getCardHolder(cardHolderId));
                    accountService.addCard(currentAccountId, cardService.getCard(cardId));
                    showCardMenu(userId, currentAccountId);
                }
                catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
                    showCardMenu(userId, currentAccountId);
                }
                break;
            case 3:
                // delete card
                try {
                    System.out.println("Enter card id: ");
                    int cardId = readInt();
                    int cardHolderId = cardService.getCard(cardId).getCardHolder().getCardHolderId();
                    cardHolderService.deleteCardHolder(cardHolderId);
                    cardTransactionService.deleteCardTransactionsByCardId(cardId);
                    cardService.deleteCard(cardId);
                    accountService.deleteCard(currentAccountId, cardId);
                    showCardMenu(userId, currentAccountId);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    showCardMenu(userId, currentAccountId);
                }
                break;
            case 4:
                // manage transactions
                try {
                    System.out.println("Enter card id: ");
                    int cardId = readInt();
                    if (cardService.getCard(cardId) == null) {
                        throw new InvalidDataException("Invalid card id");
                    }
                    showTransactionMenu(userId, currentAccountId, cardId);
                }
                catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
                    showCardMenu(userId, currentAccountId);
                }
                break;
            case 5:
                // back
                showCurrentAccountMenu(userId, true);
                break;
        }
    }

    private void executeTransactionOption(int option, int userId, int currentAccountId, int cardId) throws InvalidDataException {
        switch(option) {
            case 1:
                // view transactions
                try {
                    for (CardTransaction cardTransaction : cardService.getCardTransactions(cardId)) {
                        System.out.println(cardTransaction);
                    }
                    showTransactionMenu(userId, currentAccountId, cardId);
                }
                catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
                    showTransactionMenu(userId, currentAccountId, cardId);
                }
                break;
            case 2:
                // add transaction
                try {
                    System.out.println("Enter amount: ");
                    double amount = readDouble();
                    System.out.println("Enter description: ");
                    String description = scanner.nextLine();
                    accountService.subtractBalance(currentAccountId, amount);
                    int cardTransactionId = cardTransactionService.addCardTransaction(cardId, amount, description);
                    cardService.addCardTransaction(cardId, cardTransactionService.getCardTransaction(cardTransactionId));
                    showTransactionMenu(userId, currentAccountId, cardId);
                }
                catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
                    showTransactionMenu(userId, currentAccountId, cardId);
                }
                break;
            case 3:
                // back
                showCardMenu(userId, currentAccountId);
                break;
        }
    }

    private void executeTransferMenu(int option, int userId, int currentAccountId) throws InvalidDataException {
        switch (option) {
            case 1:
                // view transfers
                try {
                    for (Transfer transfer : transferService.getTransfersByAccountId(currentAccountId)) {
                        System.out.println(transfer);
                    }
                    showTransferMenu(userId, currentAccountId);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    showTransferMenu(userId, currentAccountId);
                }
                break;
            case 2:
                // add transfer
                try {
                    System.out.println("Enter recipient iban: ");
                    String recipientIban = scanner.nextLine();
                    System.out.println("Enter amount: ");
                    double amount = readDouble();
                    System.out.println("Enter description: ");
                    String description = scanner.nextLine();
                    Account recipientAccount = accountService.getAccountByIban(recipientIban);
                    if (recipientAccount == null || !(recipientAccount instanceof CurrentAccount)) {
                        throw new InvalidDataException("Invalid recipient iban");
                    }
                    int recipientAccountId = recipientAccount.getAccountId();
                    accountService.subtractBalance(currentAccountId, amount);
                    accountService.addBalance(recipientAccountId, amount);
                    transferService.addTransfer(currentAccountId, recipientAccountId, amount, description);
                    showTransferMenu(userId, currentAccountId);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    showTransferMenu(userId, currentAccountId);
                }
                break;
            case 3:
                // back
                showCurrentAccountMenu(userId, true);
                break;
        }
    }

    private int readOption(int a, int b) {
        try {
            int option = readInt();
            if (option >= a && option <= b) {
                return option;
            }
        }
        catch (InvalidDataException invalid) {}
        System.out.println("Invalid option. Try again");
        return readOption(a, b);
    }

    private int readOption2(int[] validOptions) {
        try {
            int option = readInt();
            for (int validOption : validOptions) {
                if (option == validOption) {
                    return option;
                }
            }
        }
        catch (InvalidDataException invalid) {}
        System.out.println("Invalid option. Try again");
        return readOption2(validOptions);
    }

    private int readInt() throws InvalidDataException {
        String line = scanner.nextLine();
        if (line.matches("^\\d+$")) {
            return Integer.parseInt(line);
        }
        else {
            throw new InvalidDataException("Invalid number");
        }
    }

    private double readDouble() throws InvalidDataException {
        String line = scanner.nextLine();
        if (line.matches("^-?\\d*\\.?\\d+$")) {
            return Double.parseDouble(line);
        }
        else {
            throw new InvalidDataException("Invalid number");
        }
    }

    private void addData() throws InvalidDataException {
        int userId = userService.registerUser("user1", "user1", "123", "user1", "123");
        int currentAccountId = accountService.addCurrentAccount(userId, 100);
        int cardId = cardService.addCard(currentAccountId);
        int cardHolderId = cardHolderService.addCardHolder("user1", "user1", "123", cardId);
        cardService.setCardHolder(cardId, cardHolderService.getCardHolder(cardHolderId));
        accountService.addCard(currentAccountId, cardService.getCard(cardId));
        System.out.println("user 1 iban: " + accountService.getAccount(currentAccountId).getIban());

        userId = userService.registerUser("user2", "user2", "124", "user2", "124");
        currentAccountId = accountService.addCurrentAccount(userId, 100);
        cardId = cardService.addCard(currentAccountId);
        cardHolderId = cardHolderService.addCardHolder("user2", "user2", "124", cardId);
        cardService.setCardHolder(cardId, cardHolderService.getCardHolder(cardHolderId));
        accountService.addCard(currentAccountId, cardService.getCard(cardId));
        System.out.println("user 2 iban: " + accountService.getAccount(currentAccountId).getIban());
    }
}
