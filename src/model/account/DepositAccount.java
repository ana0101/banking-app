package model.account;

import java.util.Calendar;
import java.util.Date;

public class DepositAccount extends Account {
    private DepositType depositType;
    private Date lastRenewalDate;

    public DepositAccount(int userId, double balance, DepositType depositType) {
        super(userId, balance);
        this.depositType = depositType;
        this.lastRenewalDate = Calendar.getInstance().getTime();  // current date
    }

    public DepositAccount(int accountId, double balance, String iban, int userId, Date lastRenewalDate, DepositType depositType) {
        super(accountId, balance, iban, userId);
        this.lastRenewalDate = lastRenewalDate;
        this.depositType = depositType;
    }

    @Override
    public String toString() {
        return "Deposit Account:\n" +
                "  account id = " + accountId + "\n" +
                "  balance = " + balance + "\n" +
                "  iban = '" + iban +  "\n" +
                "  last renewal date = " + lastRenewalDate + "\n" +
                " " + depositType;
    }

    public DepositType getDepositType() {
        return depositType;
    }

    public Date getLastRenewalDate() {
        return lastRenewalDate;
    }
}
