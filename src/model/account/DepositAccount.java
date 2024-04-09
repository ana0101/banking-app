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

    @Override
    public String toString() {
        return "DepositAccount{" +
                "depositType=" + depositType +
                ", lastRenewalDate=" + lastRenewalDate +
                ", accountId=" + accountId +
                ", userId=" + userId +
                ", balance=" + balance +
                ", iban='" + iban + '\'' +
                '}';
    }

    public DepositType getDepositType() {
        return depositType;
    }

    public Date getLastRenewalDate() {
        return lastRenewalDate;
    }

    public void setLastRenewalDate(Date lastRenewalDate) {
        this.lastRenewalDate = lastRenewalDate;
    }
}
