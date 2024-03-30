package model.account;

public abstract class Account {
    protected static int accountIdCount = 0;
    protected int accountId;
    protected int userId;
    protected double balance;
    protected String iban;

    public Account(int userId, double balance, String iban) {
        accountIdCount ++;
        this.accountId = accountIdCount;
        this.userId = userId;
        this.balance = balance;
        this.iban = iban;
    }
}
