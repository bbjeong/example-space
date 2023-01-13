package com.example.bbchu.doon;

import io.reactivex.Observable;

import java.util.logging.Logger;

public class DoOnMethod {

    private static Logger logger = Logger.getLogger(DoOnMethod.class.getName());

    private enum MethodType {
        SUBSCRIBE,
        NEXT,
        COMPLETE,
        ERROR
    }

    public static void main(String[] args) throws InterruptedException {

        MethodType methodType = MethodType.ERROR;

        switch (methodType) {
            case SUBSCRIBE -> subscribe();
            case NEXT -> next();
            case COMPLETE -> complete();
            case ERROR -> error();
        }

    }

    private static void subscribe() {
        Observable.just(1, 2, 3, 4)
                .doOnSubscribe(disposable -> logger.info("생산자 - 구독처리 준비 완료"))
                .subscribe(
                        data -> logger.info(String.valueOf(data))
                        , error -> logger.info("에러 발생 " + error.getMessage())
                        , () -> logger.warning("complete")
                        , disposable -> logger.info("소비자: 구독처리 준비 완료 알림 받음 - " + disposable.toString())
                );
    }

    private static void next() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8)
                .doOnNext(data -> logger.info("원본 통지 데이터 - " + data))
                .filter(data -> data < 6)
                .doOnNext(data -> logger.info("filter 적용 후 통지 데이터 - " + data))
                .map(data -> "###" + data + "###")
                .doOnNext(data -> logger.info("map 적용 후 통지 데이터 - " + data))
                .subscribe(data -> logger.info("최종데이터 - " + data));
    }

    private static void complete() {
        Observable.range(1, 5)
                .doOnComplete(() -> logger.info("생산자: 데이터 통지 완료"))
                .subscribe(
                        data -> logger.info(String.valueOf(data))
                        , error -> logger.warning(String.valueOf(error.getMessage()))
                        , () -> logger.info("완료")
                );
    }

    private static void error() {
        Observable.just(1, 2, 3)
                .zipWith(Observable.just(1, 0), (a, b) -> a / b)
                .doOnError(e -> logger.warning("에러발생: " + e.getMessage()))
                .subscribe(
                        data -> logger.info("데이터 " + data)
                        , error -> logger.warning(error.getMessage())
                );
    }
}
