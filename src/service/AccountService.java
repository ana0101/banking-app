package service;

import exception.InvalidDataException;
import model.account.Account;
import model.account.CurrentAccount;
import model.account.DepositAccount;
import model.account.DepositType;
import model.card.Card;
import persistence.AccountRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class AccountService {
    public AccountRepository accountRepository = new AccountRepository();

    public Account getAccount(int accountId) {
        return accountRepository.get(accountId);
    }

    public Account getAccountByIban(String iban) {
        return accountRepository.getByIban(iban);
    }

    public List<Account> getUserAccounts(int userId) {
        return accountRepository.getUserAccounts(userId);
    }

    public void addBalance(int accountId, double amount) throws InvalidDataException {
        Account account = getAccount(accountId);
        if (account == null) {
            throw new InvalidDataException("Invalid account id");
        }
        if (amount < 0) {
            throw new InvalidDataException("Invalid amount: must be positive");
        }
        accountRepository.addBalance(account, amount);
    }

    public void subtractBalance(int accountId, double amount) throws InvalidDataException {
        Account account = getAccount(accountId);
        if (account == null) {
            throw new InvalidDataException("Invalid account id");
        }
        if (amount < 0) {
            throw new InvalidDataException("Invalid amount: must be positive");
        }
        if (amount > account.getBalance()) {
            throw new InvalidDataException("Invalid amount: must be smaller than or equal to balance");
        }
        accountRepository.subtractBalance(account, amount);
    }

    // current account
    public int addCurrentAccount(int userId, double balance) throws InvalidDataException {
        if (balance < 0) {
            throw new InvalidDataException("Invalid balance");
        }
        Account account = new CurrentAccount(userId, balance);
        return accountRepository.add(account);
    }

    public Account getUserCurrentAccount(int userId) {
        return accountRepository.getUserCurrentAccount(userId);
    }

    public Card getCard(int currentAccountId, int cardId) throws InvalidDataException {
        Account currentAccount = getAccount(currentAccountId);
        if (currentAccount == null) {
            throw new InvalidDataException("Invalid current account id");
        }
        return accountRepository.getCard((CurrentAccount) currentAccount, cardId);
    }

    public HashSet<Card> getCards(int currentAccountId) throws InvalidDataException {
        Account account = getAccount(currentAccountId);
        if (account == null || !(account instanceof CurrentAccount)) {
            throw new InvalidDataException("Invalid current account id");
        }
        return accountRepository.getCards((CurrentAccount) account);
    }

    public void addCard(int currentAccountId, Card card) throws InvalidDataException {
        Account account = getAccount(currentAccountId);
        if (!(account instanceof CurrentAccount)) {
            throw new InvalidDataException("Invalid current account id");
        }
        accountRepository.addCard((CurrentAccount) account, card);
    }

    public void deleteCard(int currentAccountId, int cardId) throws InvalidDataException {
        Account account = getAccount(currentAccountId);
        if (account == null || !(account instanceof CurrentAccount)) {
            throw new InvalidDataException("Invalid current account id");
        }
        Card card = accountRepository.getCard((CurrentAccount) account, cardId);
        if (card == null) {
            throw new InvalidDataException("Invalid card id");
        }
        accountRepository.deleteCard((CurrentAccount) account, card);
    }

    // deposit account
    public int addDepositAccount(int userId, double balance, DepositType depositType) {
        Account account = new DepositAccount(userId, balance, depositType);
        return accountRepository.add(account);
    }

    public List<Account> getUserDepositAccounts(int userId) {
        return accountRepository.getUserDepositAccounts(userId);
    }

    public void updateDepositAccount(int depositAccountId) throws InvalidDataException {
        Account account = getAccount(depositAccountId);
        if (account == null || !(account instanceof DepositAccount)) {
            throw new InvalidDataException("Invalid deposit account id");
        }
        accountRepository.updateDepositAccount((DepositAccount) account);
    }

    public void deleteDepositAccount(int depositAccountId) throws InvalidDataException {
        Account account = getAccount(depositAccountId);
        if (account == null) {
            throw new InvalidDataException("Invalid deposit account id");
        }
        if (!(account instanceof DepositAccount)) {
            throw new InvalidDataException("Invalid deposit account id");
        }
        accountRepository.delete(account);
    }
}
