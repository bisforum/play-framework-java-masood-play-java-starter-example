package services;

import java.util.Date;

public class Transaction {
    Date timeStamp;
    int amount;

    public Transaction(Date timeStamp, int amout) {
        this.timeStamp = timeStamp;
        this.amount = amout;
    }

}
