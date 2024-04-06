package model.account;

import java.util.Date;

public class DepositAccount extends Account {
    private double interestRate;
    private int monthsDuration;
    private Date lastRenewalDate;

    public DepositAccount(int userId, double balance, double interestRate, int monthsDuration) {
        super(userId, balance);
        this.interestRate = interestRate;
        this.monthsDuration = monthsDuration;
    }

    @Override
    public String toString() {
        return "DepositAccount{" +
                "interestRate=" + interestRate +
                ", monthsDuration=" + monthsDuration +
                ", lastRenewalDate=" + lastRenewalDate +
                ", accountId=" + accountId +
                ", userId=" + userId +
                ", balance=" + balance +
                ", iban='" + iban + '\'' +
                '}';
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getMonthsDuration() {
        return monthsDuration;
    }

    public void setMonthsDuration(int monthsDuration) {
        this.monthsDuration = monthsDuration;
    }

    public Date getLastRenewalDate() {
        return lastRenewalDate;
    }

    public void setLastRenewalDate(Date lastRenewalDate) {
        this.lastRenewalDate = lastRenewalDate;
    }
}
