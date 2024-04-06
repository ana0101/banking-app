package persistence;

import model.account.Account;
import model.account.CurrentAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountRepository implements GenericRepository<Account> {
    private ArrayList<Account> accounts = new ArrayList<>();

    @Override
    public void add(Account account) {
        if (!accounts.contains(account)) {
            accounts.add(account);
        }
    }

    @Override
    public Account get(int id) {
        return accounts.stream().filter(account -> account.getAccountId() == id).findFirst().orElse(null);
    }

    public List<Account> getUserAccounts(int userId) {
        return accounts.stream().filter(account -> account.getUserId() == userId).collect(Collectors.toList());
    }

    public Account getUserCurrentAccount(int userId) {
        return accounts.stream()
                .filter(account -> account.getUserId() == userId && account instanceof CurrentAccount)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Account account) {

    }

    @Override
    public void delete(Account account) {
        accounts.remove(account);
    }
}
