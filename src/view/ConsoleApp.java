package view;

import config.DataSetup;
import exception.InvalidDataException;
import model.account.Account;
import model.account.CurrentAccount;
import model.account.DepositType;
import model.card.Card;
import model.card.CardTransaction;
import model.transfer.Transfer;
import model.user.User;
import persistence.DepositTypeRepository;
import service.*;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ConsoleApp {
    private Scanner scanner = new Scanner(System.in);
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private UserService userService = new UserService();
    private AccountService accountService = new AccountService();
    private CardService cardService = new CardService();
    private CardHolderService cardHolderService = new CardHolderService();
    private CardTransactionService cardTransactionService = new CardTransactionService();
    private TransferService transferService = new TransferService();
    private DepositTypeRepository depositTypeRepository = new DepositTypeRepository();
    private DataSetup dataSetup = new DataSetup();

    public static void main(String args[]) throws InvalidDataException {
        ConsoleApp app = new ConsoleApp();
        app.setUpData();
        app.scheduleUpdateDepositAccounts();
        app.showUserMenu();
    }

    private void setUpData() {
        try {
            dataSetup.createInsertFunctions();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showUserMenu() throws InvalidDataException {
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        int option = readOption(1, 3);
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

    private void showDepositAccountsMenu(int userId) throws InvalidDataException {
        System.out.println("1. View deposit accounts");
        System.out.println("2. Add deposit account");
        System.out.println("3. Dissolve deposit account");
        System.out.println("4. Back");
        int option = readOption(1,4);
        executeDepositAccountOption(option, userId);
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
                boolean hasCurrentAccount = accountService.getUserCurrentAccount(userId) != null;
                showCurrentAccountMenu(userId, hasCurrentAccount);
                break;
            case 3:
                // manage deposit accounts
                if (accountService.getUserCurrentAccount(userId) == null) {
                    System.out.println("You must have a current account in order to create a deposit");
                    showAccountMenu(userId);
                }
                else {
                    showDepositAccountsMenu(userId);
                }
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

    private void executeDepositAccountOption(int option, int userId) throws InvalidDataException {
        switch (option) {
            case 1:
                // view deposits
                for (Account account : accountService.getUserDepositAccounts(userId)) {
                    System.out.println(account);
                }
                showDepositAccountsMenu(userId);
                break;
            case 2:
                // add deposit
                try {
                    System.out.println("Enter amount: ");
                    double amount = readDouble();
                    System.out.println("Deposit types: ");
                    for (DepositType depositType : depositTypeRepository.getAll()) {
                        System.out.println(depositType);
                    }
                    System.out.println("Enter deposit type id: ");
                    int typeId = readInt();
                    DepositType depositType = depositTypeRepository.get(typeId);
                    if (depositType == null) {
                        throw new InvalidDataException("Invalid deposit type id");
                    }
                    Account currentAccount = accountService.getUserCurrentAccount(userId);
                    // move the money from the current account to the deposit
                    accountService.subtractBalance(currentAccount.getAccountId(), amount);
                    accountService.addDepositAccount(userId, amount, depositType);
                    showDepositAccountsMenu(userId);
                }
                catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
                    showDepositAccountsMenu(userId);
                }
                break;
            case 3:
                // delete deposit
                try {
                    System.out.println("Enter deposit account id: ");
                    int depositAccountId = readInt();
                    // move the money from the deposit to the current account
                    Account currentAccount = accountService.getUserCurrentAccount(userId);
                    accountService.addBalance(currentAccount.getAccountId(), accountService.getAccount(depositAccountId).getBalance());
                    accountService.deleteDepositAccount(depositAccountId);
                    showDepositAccountsMenu(userId);
                }
                catch (InvalidDataException e) {
                    System.out.println(e.getMessage());
                    showDepositAccountsMenu(userId);
                }
                break;
            case 4:
                // back
                showAccountMenu(userId);
                break;
        }
    }

    private void executeCardOption(int option, int userId, int currentAccountId) throws InvalidDataException {
        switch (option) {
            case 1:
                // view cards
                for (Card card : cardService.getCardsByCurrentAccountId(currentAccountId)) {
                    System.out.println(card);
                }
                showCardMenu(userId, currentAccountId);
                break;
            case 2:
                // add card
                try {
                    int cardId = cardService.addCard(currentAccountId);
                    System.out.println("Enter holder first name: ");
                    String holderFirstName = scanner.nextLine();
                    System.out.println("Enter holder last name: ");
                    String holderLastName = scanner.nextLine();
                    System.out.println("Enter holder cnp: ");
                    String holderCnp = scanner.nextLine();
                    cardHolderService.addCardHolder(holderFirstName, holderLastName, holderCnp, cardId);
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
                    cardService.deleteCard(cardId);
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
                    for (CardTransaction cardTransaction : cardTransactionService.getCardTransactionsByCardId(cardId)) {
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
                    cardTransactionService.addCardTransaction(cardId, amount, description);
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
                    Account recipientAccount = accountService.getCurrentAccountByIban(recipientIban);
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

    private void updateDepositAccounts() {
        try {
            for (User user : userService.getAllUsers()) {
                for (Account depositAccount : accountService.getUserDepositAccounts(user.getUserId())) {
                    accountService.updateDepositAccount(depositAccount.getAccountId());
                }
            }
        }
        catch (InvalidDataException e) {
            System.out.println("Error updating deposit accounts: " + e.getMessage());
        }
    }

    private void scheduleUpdateDepositAccounts() {
        // update the deposit accounts once every 2 minutes
        scheduler.scheduleAtFixedRate(this::updateDepositAccounts, 0, 1, TimeUnit.MINUTES);
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
}
