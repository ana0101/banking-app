package model.account;

public class DepositType {
    private static int depositTypeIdCount = 0;
    private final int depositTypeId;
    private final int monthsDuration;
    private final double interestRate;

    public DepositType(int monthsDuration, double interestRate) {
        depositTypeIdCount ++;
        this.depositTypeId = depositTypeIdCount;
        this.monthsDuration = monthsDuration;
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "DepositType{" +
                "depositTypeId=" + depositTypeId +
                ", monthsDuration=" + monthsDuration +
                ", interestRate=" + interestRate +
                '}';
    }

    public int getDepositTypeId() {
        return depositTypeId;
    }

    public int getMonthsDuration() {
        return monthsDuration;
    }

    public double getInterestRate() {
        return interestRate;
    }
}
