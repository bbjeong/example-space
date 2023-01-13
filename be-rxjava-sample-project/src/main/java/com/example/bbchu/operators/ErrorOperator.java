package com.example.bbchu.operators;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ErrorOperator {

    private static Logger logger = Logger.getLogger(ErrorOperator.class.getName());

    private enum MethodType {
        ONERRORRETURN,
        ONERRORRESUMENEXT,
        RETRY,
        RETRY2
    }

    public static void main(String[] args) throws InterruptedException {

        MethodType methodType = MethodType.RETRY2;

        switch (methodType) {
            case ONERRORRETURN -> onErrorReturn();
            case ONERRORRESUMENEXT -> onErrorResumeNext();
            case RETRY -> retry();
            case RETRY2 -> retry2();
        }

    }

    private static void onErrorReturn() throws InterruptedException {
        Observable.just(5)
                .flatMap(num -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i -> num / i)
                        .onErrorReturn(e -> {
                            if (e instanceof ArithmeticException) {
                                logger.warning("계산 에러 발생: " + e.getMessage());
                            }
                            return -1L;
                        })
                ).subscribe(
                        data -> {
                            if (data < 0) {
                                logger.warning("예외 " + data);
                            } else {
                                logger.warning(String.valueOf(data));
                            }
                        }
                        , error -> logger.warning(String.valueOf(error))
                        , () -> logger.info("completed")
                );

        Thread.sleep(3000L);

    }

    private static void onErrorResumeNext() throws InterruptedException {
        Observable.just(5)
                .flatMap(num -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i -> {
                            long result;
                            try {
                                result = num / i;
                            } catch (Exception e) {

                                logger.warning("계산 에러 발생: " + e.getMessage());
                                throw e;
                            }
                            return result;
                        }).onErrorResumeNext(e -> {
                            logger.warning("운영자에게 이메일 발송 " + e.getMessage());

                            return Observable.interval(200L, TimeUnit.MILLISECONDS).take(5).skip(1).map(i -> num / i);
                        })
                ).subscribe(data -> logger.warning(String.valueOf(data)));

        Thread.sleep(3000L);

    }

    private static void retry() throws InterruptedException {
        Observable.just(5)
                .flatMap(num -> Observable.interval(200L, TimeUnit.MILLISECONDS)
                        .take(5)
                        .map(i -> {
                            long result;
                            try {
                                result = num / i;
                            } catch (Exception e) {
                                logger.warning("계산 에러 발생: " + e.getMessage());
                                throw e;
                            }
                            return result;
                        })
                        .retry(5)
                        .onErrorReturn(e -> -1L)
                ).subscribe(
                        data -> {
                            if (data < 0) {
                                logger.warning("예외 " + data);
                            } else {
                                logger.warning(String.valueOf(data));
                            }
                        }
                        , error -> logger.warning(String.valueOf(error))
                        , () -> logger.info("completed")
                );

        Thread.sleep(3000L);

    }

    private static void retry2() throws InterruptedException {
        Observable.just(10, 12, 15, 16)
                .zipWith(Observable.just(1, 2, 0, 4), (data1, data2) -> {

                    long result;
                    try {
                        result = data1 / data2;
                    } catch (Exception e) {
                        logger.warning("계산 에러 발생: " + e.getMessage());
                        throw e;
                    }
                    return result;
                })
                .retry(3)
                .onErrorReturn(e -> -1L)
                .subscribe(
                        data -> logger.info(String.valueOf(data))
                        , error -> logger.warning(String.valueOf(error))
                        , () -> logger.info("completed")
                );

        Thread.sleep(3000L);

    }

}
