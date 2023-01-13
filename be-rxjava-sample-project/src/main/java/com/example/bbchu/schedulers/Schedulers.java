package com.example.bbchu.schedulers;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Logger;

public class Schedulers {

    private static Logger logger = Logger.getLogger(Schedulers.class.getName());

    public static void main(String[] args) throws InterruptedException {
        Observable<Integer> observable1 = Observable.range(100, 120);
        Observable<Integer> observable2 = Observable.range(30, 50);
        Observable<Integer> observable3 = Observable.range(60, 90);

        Observable<Integer> observable4 = Observable.range(1, 24);

        Observable source = Observable.zip(observable1, observable2, observable3, observable4
                , (data1, data2, data3, hour) -> hour + "시: " + Collections.max(Arrays.asList(data1, data2, data3)));

        source.subscribeOn(io.reactivex.schedulers.Schedulers.computation())
                .subscribe(data -> logger.info("data: " + data));

        source.subscribeOn(io.reactivex.schedulers.Schedulers.computation())
                .subscribe(data -> logger.info("data: " + data));

        Thread.sleep(500L);
    }
}
