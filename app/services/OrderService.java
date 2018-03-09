package services;

import org.joda.time.DateTime;

import java.util.concurrent.*;

public interface OrderService {

    CompletionStage<Void> addOrder(Transaction date);

    CompletionStage<Statistics> getStatistics(DateTime requestTime);



}
