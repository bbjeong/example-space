package com.example.bbchu.operators;

import io.reactivex.Observable;

import java.util.logging.Logger;

public class ConditionOperator {

    private static Logger logger = Logger.getLogger(ConditionOperator.class.getName());

    private enum MethodType {
        ALL
    }

    public static void main(String[] args) throws InterruptedException {

        MethodType methodType = MethodType.ALL;

        switch (methodType) {
            case ALL -> all();
        }

    }

    private static void all() {
        Observable.just(1, 2, 3, 4)
                .all(data -> data % 3 == 0)
                .subscribe(data -> logger.info(String.valueOf(data)));
    }
}
