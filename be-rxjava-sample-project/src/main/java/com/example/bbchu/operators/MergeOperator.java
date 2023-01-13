package com.example.bbchu.operators;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MergeOperator {

    private static Logger logger = Logger.getLogger(MergeOperator.class.getName());

    private enum MethodType {
        MERGE,
        CONCAT,
        ZIP,
        COMBINELATEST
    }

    public static void main(String[] args) throws InterruptedException {

        MethodType methodType = MethodType.COMBINELATEST;

        switch (methodType) {
            case MERGE -> merge();
            case CONCAT -> concat();
            case ZIP -> zip();
            case COMBINELATEST -> combineLatest();
        }

    }

    private static void merge() throws InterruptedException {

        Observable observable1 = Observable.interval(200L, TimeUnit.MILLISECONDS)
                .take(5);
        Observable observable2 = Observable.interval(400L, TimeUnit.MILLISECONDS)
                .take(5)
                .map(data -> data + 1000);

        Observable.merge(observable1, observable2)
                .subscribe(data -> logger.info(String.valueOf(data)));

        Thread.sleep(3000L);
    }

    private static void concat() throws InterruptedException {

        Observable observable1 = Observable.interval(200L, TimeUnit.MILLISECONDS)
                .take(5);
        Observable observable2 = Observable.interval(400L, TimeUnit.MILLISECONDS)
                .take(5)
                .map(data -> data + 1000);

        Observable.concat(observable1, observable2)
                .subscribe(data -> logger.info(String.valueOf(data)));

        Thread.sleep(3000L);
    }

    private static void zip() throws InterruptedException {
        Observable<Long> observable1 = Observable.interval(200L, TimeUnit.MILLISECONDS)
                .take(5); // 0, 1, 2, 3, 4
        Observable<Long> observable2 = Observable.interval(400L, TimeUnit.MILLISECONDS)
                .take(5) // 1000, 1001, 1002, 1003, 1004
                .map(data -> data + 1000);

        Observable.zip(observable1, observable2, (data1, data2) -> data1 + data2) // 1000, 1002, 1004, 1006, 1008
                .subscribe(data -> logger.info(String.valueOf(data)));

        Thread.sleep(3000L);
    }

    private static void combineLatest() throws InterruptedException {
        Observable<Long> observable1 = Observable.interval(200L, TimeUnit.MILLISECONDS)
                .take(5); // 0, 1, 2, 3, 4
        Observable<Long> observable2 = Observable.interval(400L, TimeUnit.MILLISECONDS)
                .take(5)
                .map(data -> data + 1000);// 1000, 1001, 1002, 1003, 1004

        Observable.combineLatest(observable1, observable2
                        , (data1, data2) ->
                                "data1: " + data1 + " data2: " + data2 + " data1+data2: " + (data1 + data2)
                ) // 1000, 1002, 1004, 1006, 1008
                .subscribe(data -> logger.info(String.valueOf(data)));

        Thread.sleep(3000L);
    }
}
