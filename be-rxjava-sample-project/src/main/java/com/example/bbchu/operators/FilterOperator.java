package com.example.bbchu.operators;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class FilterOperator {

    private static Logger logger = Logger.getLogger(FilterOperator.class.getName());

    private enum MethodType {
        FILTER,
        DISTINCT,
        TAKE,
        TAKEUNTIL,
        SKIP
    }

    public static void main(String[] args) throws InterruptedException {
        MethodType type = MethodType.SKIP;

        switch (type) {
            case FILTER -> filter();
            case DISTINCT -> distinct();
            case TAKE -> take();
            case TAKEUNTIL -> takeUntil();
            case SKIP -> skip();
        }
    }

    private static void filter() {
        Observable.range(0, 5)
                .filter(data -> data > 2)
                .subscribe(data -> logger.info("filtered: " + data));
    }

    private static void distinct() {
        Observable.fromArray(Arrays.asList(1, 1, 3, 4, 5))
                .distinct()
                .subscribe(data -> logger.info("distinct: " + data));
    }

    private static void take() throws InterruptedException {
        Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .take(3500L, TimeUnit.MILLISECONDS)
                .subscribe(data -> logger.info("take: " + data));

        Thread.sleep(6000L);
    }

    private static void takeUntil() throws InterruptedException {
        Observable.interval(1000L, TimeUnit.MILLISECONDS)
                .takeUntil(Observable.timer(5500L, TimeUnit.MILLISECONDS))
                .subscribe(data -> logger.info("takeuntil: " + data));

        Thread.sleep(6000L);
    }

    private static void skip() throws InterruptedException {
        Observable.interval(300L, TimeUnit.MILLISECONDS)
                .skip(1000L, TimeUnit.MILLISECONDS)
                .subscribe(data -> logger.info("skip: " + data));

        Thread.sleep(2000L);
    }
}
