package model.account;

import java.util.Random;

public abstract class Account {
    protected static int accountIdCount = 0;
    protected int accountId;
    protected int userId;
    protected double balance;
    protected String iban;

    public Account(int userId, double balance) {
        accountIdCount ++;
        this.accountId = accountIdCount;
        this.userId = userId;
        this.balance = balance;
        this.iban = generateIban();
    }

    private static String generateIban() {
        String countryCode = "RO";

        Random random = new Random();
        int bankCode = random.nextInt(10000);
        int officeCode = random.nextInt(10000);

        StringBuilder accountNumberBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            accountNumberBuilder.append(random.nextInt(10));
        }
        String accountNumber = accountNumberBuilder.toString();

        String iban = countryCode + String.format("%04d", bankCode) + String.format("%04d", officeCode) + accountNumber;

        return iban;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", balance=" + balance +
                ", iban='" + iban + '\'' +
                '}';
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
