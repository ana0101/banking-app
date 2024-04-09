package service;

import exception.InvalidDataException;
import model.account.Account;
import model.account.CurrentAccount;
import model.card.Card;
import persistence.AccountRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class AccountService {
    public AccountRepository accountRepository = new AccountRepository();

    public int addCurrentAccount(int userId, double balance) throws InvalidDataException {
        if (balance < 0) {
            throw new InvalidDataException("Invalid balance");
        }
        Account account = new CurrentAccount(userId, balance);
        return accountRepository.add(account);
    }

    public void addDepositAccount(int userId, double balance, double interestRate, int monthsDuration, Date lastRenewalDate) {

    }

    public Account getAccount(int accountId) {
        return accountRepository.get(accountId);
    }

    public List<Account> getUserAccounts(int userId) {
        return accountRepository.getUserAccounts(userId);
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
        if (!(account instanceof CurrentAccount)) {
            throw new InvalidDataException("Invalid current account id");
        }
        Card card = accountRepository.getCard((CurrentAccount) account, cardId);
        if (card == null) {
            throw new InvalidDataException("Invalid card id");
        }
        accountRepository.deleteCard((CurrentAccount) account, card);
    }
}
