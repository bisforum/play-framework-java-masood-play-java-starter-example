package services;

import play.Configuration;

import javax.inject.Singleton;
import java.util.Date;
import java.util.concurrent.*;

public interface OrderService {

    final int queueCapacity = Integer.valueOf(Configuration.root().getString("queue.capacity"));

    @Singleton
    BlockingQueue<Transaction> orderQueue = new PriorityBlockingQueue<>(queueCapacity, Transaction::compareTo);


    CompletionStage<Void> addOrder(Transaction date);

    CompletionStage<Statistics> getStatistics(Date requestTime);



}
