package service;

import exception.InvalidDataException;
import model.account.Account;
import model.account.CurrentAccount;
import model.account.DepositAccount;
import model.account.DepositType;
import persistence.AccountRepository;

import java.util.List;

public class AccountService {
    public AccountRepository accountRepository = new AccountRepository();

    public Account getAccount(int accountId) {
        return accountRepository.get(accountId);
    }

    public List<Account> getUserAccounts(int userId) {
        return accountRepository.getUserAccounts(userId);
    }

    public Account getUserCurrentAccount(int userId) {
        return accountRepository.getUserCurrentAccount(userId);
    }

    public Account getCurrentAccountByIban(String iban) { return accountRepository.getCurrentAccountByIban(iban); }

    public List<DepositAccount> getUserDepositAccounts(int userId) {
        return accountRepository.getUserDepositAccounts(userId);
    }

    public DepositAccount getDepositAccountById(int accountId) { return accountRepository.getDepositAccountById(accountId); }

    public int addCurrentAccount(int userId, double balance) throws InvalidDataException {
        if (balance < 0) {
            throw new InvalidDataException("Invalid balance");
        }
        CurrentAccount account = new CurrentAccount(userId, balance);
        return accountRepository.addCurrentAccount(account);
    }

    public int addDepositAccount(int userId, double balance, DepositType depositType) {
        DepositAccount account = new DepositAccount(userId, balance, depositType);
        return accountRepository.addDepositAccount(account);
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

    public void updateDepositAccount(int depositAccountId) throws InvalidDataException {
        Account account = getDepositAccountById(depositAccountId);
        if (account == null || !(account instanceof DepositAccount)) {
            throw new InvalidDataException("Invalid deposit account id");
        }
        accountRepository.updateDepositAccount((DepositAccount) account);
    }

    public void deleteDepositAccount(int depositAccountId) throws InvalidDataException {
        Account account = getDepositAccountById(depositAccountId);
        if (account == null || !(account instanceof DepositAccount)) {
            throw new InvalidDataException("Invalid deposit account id");
        }
        accountRepository.delete(account);
    }
}
