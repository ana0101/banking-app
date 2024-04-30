package model.account;

import java.util.Random;

public class Account {
    protected int accountId;
    protected int userId;
    protected double balance;
    protected String iban;

    public Account(int userId, double balance) {
        this.userId = userId;
        this.balance = balance;
        this.iban = generateIban();
    }

    public Account(int accountId, double balance, String iban, int userId) {
        this.accountId = accountId;
        this.balance = balance;
        this.iban = iban;
        this.userId = userId;
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
        return "Account:\n" +
                "  account id = " + accountId + "\n" +
                "  balance = " + balance + "\n" +
                "  iban = " + iban + "\n";
    }

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public String getIban() {
        return iban;
    }
}
