package com.example.bbchu.operators;

import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ConvertOperator {

    private static Logger logger = Logger.getLogger(ConvertOperator.class.getName());

    private enum MethodType {
        MAP,
        FLATMAP,
        CONCATMAP,
        SWITCHMAP,
        TOLIST,
        TOMAP
    }

    public static void main(String[] args) {
        MethodType methodType = MethodType.TOMAP;

        switch (methodType) {
            case MAP -> map();
            case FLATMAP -> flatMap();
            case CONCATMAP -> concatMap();
            case SWITCHMAP -> switchMap();
            case TOLIST -> toList();
            case TOMAP -> toMap();
        }
    }

    private static void map() {
        Observable.fromIterable(Arrays.asList(1, 2, 3, 4, 5))
                .map(data -> data + 1)
                .subscribe(data -> logger.info(String.valueOf(data)));
    }

    private static void flatMap() {
        Observable.just("hello")
                .flatMap(data -> Observable.just("java", "python").map(lang -> data + " " + lang))
                .subscribe(data -> logger.info(String.valueOf(data)));

        Observable.range(2, 1)
                .flatMap(data -> Observable.range(1, 9).map(row -> {
                    logger.info(data + " X " + row);
                    return data * row;
                }))
                .subscribe(data -> logger.info(String.valueOf(data)));

        Observable.range(2, 1)
                .flatMap(data -> Observable.range(1, 9),
                        (source, converted) -> source + " X " + converted + " = " + (source * converted)
                ).subscribe(data -> logger.info(String.valueOf(data)));
    }

    private static void concatMap() {
        Observable.range(2, 8)
                .concatMap(data -> Observable.range(1, 9)
                        .map(row -> data + " X " + row + " = " + (data * row)))
                .subscribe(data -> logger.info(String.valueOf(data)));

    }

    private static void switchMap() {
        Observable.range(2, 8)
                .switchMap(data -> Observable.range(1, 9)
                        .map(row -> data + " X " + row + " = " + (data * row)))
                .subscribe(data -> logger.info(String.valueOf(data)));
    }

    private static void toList() {
        Single<List<Integer>> single = Observable.range(1, 10).toList();
        single.subscribe(data -> logger.info(String.valueOf(data)));
    }

    private static void toMap() {
        Single<Map<Integer, Integer>> single1 = Observable.range(1, 10)
                .toMap((data) -> data); // 인자하나인 경우 key selector
        single1.subscribe(data -> logger.info(String.valueOf(data)));

        Single<Map<Integer, Integer>> single2 = Observable.range(1, 10)
                .toMap(
                        (data) -> data,
                        (data) -> data*data
                ); // key, value
        single2.subscribe(data -> logger.info(String.valueOf(data)));
    }





}