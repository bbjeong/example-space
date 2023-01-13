package com.example.bbchu.operators;

import io.reactivex.Observable;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class CreateOperator {

    private static Logger logger = Logger.getLogger(CreateOperator.class.getName());

    private enum MethodType {
        CREATE,
        INTERVAL,
        RANGE,
        TIMER,
        DEFER,
        FROMITERABLE,
        FROMFUTURE
    }

    public static void main(String[] args) throws InterruptedException {

        MethodType type = MethodType.FROMITERABLE;

        switch (type) {
            case CREATE -> create();
            case INTERVAL -> interval();
            case RANGE -> range();
            case TIMER -> timer();
            case DEFER -> defer();
            case FROMITERABLE -> fromIterable();
            case FROMFUTURE -> fromFuture();
        }

    }

    private static void create() throws InterruptedException {
        // 얘는 메인 스레드에서 실행됨
        Observable<Integer> observable = Observable.create(subscriber -> {
            for (int i = 0; i < 5; i++) {
                subscriber.onNext(i);
            }
            subscriber.onComplete();
        });

        observable.subscribe(
                i -> logger.info("next: " + i),
                e -> logger.warning("error: "+e),
                () -> logger.info("completed")
        );

    }

    // polling 용도로 사용
    private static void interval() throws InterruptedException {
        // 독립된 스레드에서 실행
        Observable.interval(0L, 1000L, TimeUnit.MILLISECONDS)
                .map(num -> num + " count")
                .subscribe(data -> logger.info("onNext " + data));

        // 독립된 스레드에서 실행되므로 메인스레드는 슬립처리 필요
        Thread.sleep(3000);
    }

    private static void range() {
        Observable<Integer> observable = Observable.range(0, 5);
        observable = observable.map(num -> num + num);
        observable.subscribe(data -> logger.info("onNext " + data));
    }

    private static void timer() throws InterruptedException {
        // 독립된 스레드에서 실행 (2초뒤 실행)
        Observable<String> observable = Observable.timer(2000, TimeUnit.MILLISECONDS)
                .map(count -> "Do Work");
        observable.subscribe(data -> logger.info("onNext " + data));
        Thread.sleep(3000);
    }

    private static void defer() throws InterruptedException {
        // subscribe 가 호출될 때마다 새로운 observable 생성
        // just 는 just 실행한 시점, defer 는 구독시점에 실행
        Observable<LocalTime> observableDefer = Observable.defer(() -> Observable.just(LocalTime.now()));
        Observable<LocalTime> observableJust = Observable.just(LocalTime.now());

        observableDefer.subscribe(time -> logger.info("defer " + time));
        observableJust.subscribe(time -> logger.info("just " + time));

        Thread.sleep(3000);

        observableDefer.subscribe(time -> logger.info("defer " + time));
        observableJust.subscribe(time -> logger.info("just " + time));
    }

    private static void fromIterable() {

        List<String> array = Arrays.asList("a", "b", "c", "d");
        Observable.fromIterable(array)
                .subscribe(alpha -> logger.info(alpha));

    }

    private static void fromFuture() {
        Future<Double> future = longtimeWork();

        shorttimeWork();

        Observable.fromFuture(future)
                .subscribe(data -> logger.info("긴 처리시간 작업 결과 " + data));

        logger.info("the end");
    }

    private static void shorttimeWork() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            // do nothing
        }
        logger.info("짧은 처리시간이 걸리는 작업");
    }

    private static CompletableFuture<Double> longtimeWork() {
        return CompletableFuture.supplyAsync(() -> calculate());
    }

    private static Double calculate() {
        logger.info("긴 처리시간이 걸리는 작업");
        try {
            Thread.sleep(6000L);
        } catch (Exception e) {
            return 0.0;
        }
        return 100000000.0;
    }


}
