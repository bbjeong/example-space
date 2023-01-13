package com.example.bbchu.operators;

import io.reactivex.Observable;

import java.util.logging.Logger;

public class CountOperator {

    private static Logger logger = Logger.getLogger(CountOperator.class.getName());

    private enum MethodType {
        COUNT,
        REDUCE,
        SCAN,
        REDUCEEXAMPLE
    }

    public static void main(String[] args) throws InterruptedException {

        MethodType methodType = MethodType.REDUCEEXAMPLE;

        switch (methodType) {
            case COUNT -> count();
            case REDUCE -> reduce();
            case SCAN -> scan();
            case REDUCEEXAMPLE -> reduceExample();
        }

    }

    private static void count() {
        Observable.just(1, 2, 3, 4, 5)
                .doOnNext(data -> {
                    logger.info("doOnNext: " + data);
                }).count()
                .subscribe(data -> {
                    logger.info("subscribe: " + data);
                });
    }

    private static void reduce() {
        Observable.just("1", "2", "3", "4", "5")
                .doOnNext(data -> {
                    logger.info("doOnNext: " + data);
                }).reduce((a, b) -> "(" + a + ", " + b + ")")
                .subscribe(data -> {
                    logger.info(String.valueOf(data));
                });
    }

    private static void scan() {
        Observable.just("1", "2", "3", "4", "5")
                .doOnNext(data -> {
                    logger.info("doOnNext: " + data);
                }).scan((a, b) -> "(" + a + ", " + b + ")")
                .subscribe(data -> {
                    logger.info(String.valueOf(data));
                });
    }

    private static void reduceExample() {
        Observable.just(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
                .doOnNext(data -> {
                    logger.info("doOnNext: " + data);
                }).reduce((a, b) -> a - b)
                .subscribe(data -> {
                    logger.info(String.valueOf(data));
                });
    }
}
