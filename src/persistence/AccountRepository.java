package persistence;

import model.account.Account;
import model.account.CurrentAccount;
import model.card.Card;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class AccountRepository implements GenericRepository<Account> {
    private ArrayList<Account> accounts = new ArrayList<>();

    @Override
    public int add(Account account) {
        if (!accounts.contains(account)) {
            accounts.add(account);
            return account.getAccountId();
        }
        return 0;
    }

    @Override
    public Account get(int id) {
        return accounts.stream()
                .filter(account -> account.getAccountId() == id)
                .findFirst()
                .orElse(null);
    }

    public Account getByIban(String iban) {
        return accounts.stream()
                .filter(account -> account.getIban().equals(iban))
                .findFirst()
                .orElse(null);
    }

    public List<Account> getUserAccounts(int userId) {
        return accounts.stream()
                .filter(account -> account.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public Account getUserCurrentAccount(int userId) {
        return accounts.stream()
                .filter(account -> account.getUserId() == userId && account instanceof CurrentAccount)
                .findFirst()
                .orElse(null);
    }

    public Card getCard(CurrentAccount currentAccount, int cardId) {
        return currentAccount.getCard(cardId);
    }

    public HashSet<Card> getCards(CurrentAccount currentAccount) {
        return currentAccount.getCards();
    }

    public void addCard(CurrentAccount currentAccount, Card card) {
        currentAccount.addCard(card);
    }

    public void deleteCard(CurrentAccount currentAccount, Card card) {
        currentAccount.deleteCard(card);
    }

    @Override
    public void update(Account account) {

    }

    public void addBalance(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
    }

    public void substractBalance(Account account, double amount) {
        account.setBalance(account.getBalance() - amount);
    }

    @Override
    public void delete(Account account) {
        accounts.remove(account);
    }
}
