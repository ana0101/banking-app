package service;

import exception.InvalidDataException;
import model.account.Account;
import model.account.CurrentAccount;
import persistence.AccountRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AccountService {
    private AccountRepository accountRepository = new AccountRepository();

    public void addCurrentAccount(int userId, double balance) throws InvalidDataException {
        if (balance < 0) {
            throw new InvalidDataException("Invalid balance");
        }
        Account account = new CurrentAccount(userId, balance);
        accountRepository.add(account);
    }

    public void addDepositAccount(int userId, double balance, double interestRate, int monthsDuration, Date lastRenewalDate) {

    }

    public List<Account> getUserAccounts(int userId) {
        return accountRepository.getUserAccounts(userId);
    }

    public Account getUserCurrentAccount(int userId) {
        return accountRepository.getUserCurrentAccount(userId);
    }
}
