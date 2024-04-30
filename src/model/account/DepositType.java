package model.account;

public class DepositType {
    private final int depositTypeId;
    private final int monthsDuration;
    private final double interestRate;

    public DepositType(int depositTypeId, int monthsDuration, double interestRate) {
        this.depositTypeId = depositTypeId;
        this.monthsDuration = monthsDuration;
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "Deposit Type:\n" +
                "  deposit type id = " + depositTypeId + "\n" +
                "  months duration = " + monthsDuration + "\n" +
                "  interest rate = " + interestRate + "\n";
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
