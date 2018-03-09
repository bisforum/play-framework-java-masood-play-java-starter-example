package services;

import org.joda.time.DateTime;
import play.Configuration;

import javax.inject.Singleton;
import java.util.Date;
import java.util.concurrent.*;

public interface OrderService {

    CompletionStage<Void> addOrder(Transaction date);

    CompletionStage<Statistics> getStatistics(DateTime requestTime);



}
