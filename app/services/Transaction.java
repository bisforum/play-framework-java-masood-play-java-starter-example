package services;

import java.util.Date;
import java.util.Optional;

public class Transaction implements Comparable<Transaction> {
    Date timeStamp;
    int amount;
//    Optional<Integer> sum;

    public Transaction(Date timeStamp, int amout) {
        this.timeStamp = timeStamp;
        this.amount = amout;
//        this.sum = sum;
    }

    @Override
    public int compareTo(Transaction another) {
        return this.timeStamp.compareTo(another.timeStamp);
    }
}
