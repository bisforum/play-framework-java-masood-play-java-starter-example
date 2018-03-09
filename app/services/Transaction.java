package services;

import org.joda.time.DateTime;

public class Transaction implements Comparable<Transaction> {
    DateTime timeStamp;
    int amount;

    public Transaction(DateTime timeStamp, int amout) {
        this.timeStamp = timeStamp;
        this.amount = amout;
    }

    @Override
    public int compareTo(Transaction another) {
        return this.timeStamp.compareTo(another.timeStamp);
    }
}
