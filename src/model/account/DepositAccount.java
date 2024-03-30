package model.account;

import java.util.Date;

public class DepositAccount extends Account {
    private double interestRate;
    private int monthsDuration;
    private Date lastRenewalDate;

    public DepositAccount(int userId, double balance, String iban, double interestRate, int monthsDuration) {
        super(userId, balance, iban);
        this.interestRate = interestRate;
        this.monthsDuration = monthsDuration;
        this.lastRenewalDate = new Date();
    }
}
