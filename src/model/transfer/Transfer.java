package model.transfer;

import java.util.Calendar;
import java.util.Date;

public class Transfer {
    private static int transferIdCount = 0;
    private int transferId;
    private int payerAccountId;
    private int recipientAccountId;
    private double amount;
    private Date date;
    private String description;

    public Transfer(int payerAccountId, int recipientAccountId, double amount, Date date, String description) {
        transferIdCount ++;
        this.transferId = transferIdCount;
        this.payerAccountId = payerAccountId;
        this.recipientAccountId = recipientAccountId;
        this.amount = amount;
        this.date = Calendar.getInstance().getTime();  // current date
        this.description = description;
    }
}
