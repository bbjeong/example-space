package com.example.bbchu.operators;

import io.reactivex.Observable;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class UtilityOperator {

    private static Logger logger = Logger.getLogger(UtilityOperator.class.getName());

    private enum MethodType {
        DELAY,
        DELAY2,
        DELAYSUBSCRIPTION,
        TIMEOUT,
        TIMEINTERVAL
    }

    public static void main(String[] args) throws InterruptedException {

        MethodType methodType = MethodType.TIMEINTERVAL;

        switch (methodType) {
            case DELAY -> delay();
            case DELAY2 -> delay2();
            case DELAYSUBSCRIPTION -> delaySubscription();
            case TIMEOUT -> timeout();
            case TIMEINTERVAL -> timeInterval();
        }

    }

    private static void delay() throws InterruptedException {
        logger.info(String.valueOf(LocalDateTime.now()));
        Observable.just(1, 3, 4, 6)
                .doOnNext(data -> logger.info(String.valueOf(data)))
                .delay(2000L, TimeUnit.MILLISECONDS)
                .subscribe(data -> logger.info(String.valueOf(data)));

        Thread.sleep(3000L);
    }

    private static void delay2() throws InterruptedException {
        logger.info(String.valueOf(LocalDateTime.now()));
        Observable.just(1, 3, 4, 6)
                .delay(item -> {
                    Thread.sleep(1000L);
                    return Observable.just(item);
                })
                .subscribe(data -> logger.info(String.valueOf(data)));
    }

    private static void delaySubscription() throws InterruptedException {
        logger.info(String.valueOf(LocalDateTime.now()));
        Observable.just(1, 3, 4, 6)
                .doOnNext(data -> logger.info(String.valueOf(data)))
                .delaySubscription(2000L, TimeUnit.MILLISECONDS)
                .subscribe(data -> logger.info(String.valueOf(data)));

        Thread.sleep(3000L);
    }

    private static void timeout() throws InterruptedException {

        Observable.range(1, 5)
                .map(data -> {
                    long time = 1000L;
                    if (data == 4) {
                        time = 1500L;
                    }
                    Thread.sleep(time);
                    return time;
                })
                .timeout(1200L, TimeUnit.MILLISECONDS)
                .subscribe(
                        data -> logger.info(String.valueOf(data))
                        , e -> logger.warning(String.valueOf(e))
                        , () -> logger.info("completed")
                );

        Thread.sleep(5000L);
    }

    private static void timeInterval() throws InterruptedException {

        Observable.range(1, 5)
                .delay(item -> {
                    Thread.sleep((long) Math.random() * 10000L);
                    return Observable.just(item);
                })
                .timeInterval()
                .subscribe(
                        data -> logger.info(data + " " + data.time())
                        , e -> logger.warning(String.valueOf(e))
                        , () -> logger.info("completed")
                );

        Thread.sleep(5000L);
    }
}
