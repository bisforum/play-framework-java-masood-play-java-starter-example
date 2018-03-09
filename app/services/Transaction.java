package services;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.Optional;

public class Transaction implements Comparable<Transaction> {
    DateTime timeStamp;
    int amount;
//    Optional<Integer> sum;

    public Transaction(DateTime timeStamp, int amout) {
        this.timeStamp = timeStamp;
        this.amount = amout;
//        this.sum = sum;
    }

    @Override
    public int compareTo(Transaction another) {
        return this.timeStamp.compareTo(another.timeStamp);
    }
}
