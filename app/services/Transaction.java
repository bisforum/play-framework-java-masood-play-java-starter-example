package services;

import java.util.Date;
import java.util.Optional;

public class Transaction {
    Date timeStamp;
    int amount;
//    Optional<Integer> sum;

    public Transaction(Date timeStamp, int amout) {
        this.timeStamp = timeStamp;
        this.amount = amout;
//        this.sum = sum;
    }

}
