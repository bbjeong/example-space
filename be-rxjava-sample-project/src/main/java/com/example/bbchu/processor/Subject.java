package com.example.bbchu.processor;

import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

import java.util.logging.Logger;

public class Subject {
    private static Logger logger = Logger.getLogger(Subject.class.getName());

    private enum MethodType {
        PUBLISH,
        ASYNC,
        BEHAVIOR,
        REPLAY,
        REPLAY2
    }

    public static void main(String[] args) {

        MethodType methodType = MethodType.REPLAY2;
        switch (methodType) {
            case PUBLISH -> publish();
            case ASYNC -> async();
            case BEHAVIOR -> behavior();
            case REPLAY -> replay();
            case REPLAY2 -> replay2();
        }

    }

    private static void publish() {

        PublishSubject subject = PublishSubject.create();

        subject.subscribe(data -> logger.info("소비자1 - data: " + data));
        subject.onNext(3500);
        subject.subscribe(data -> logger.info("소비자2 - data: " + data));
        subject.onNext(3400);
        subject.subscribe(data -> logger.info("소비자3 - data: " + data));
        subject.onNext(3300);

        subject.subscribe(data -> logger.info("소비자4 - data: " + data)
                , error -> logger.warning("소비자4 - 에러: " + error)
                , () -> logger.info("소비자4 - oncomplete"));

        subject.onComplete();

    }

    private static void async() {

        AsyncSubject subject = AsyncSubject.create();
        subject.onNext(1000);

        subject.doOnNext(data -> logger.info("소비자1 - onNext: " + data))
                .subscribe(data -> logger.info("소비자1 - subscribe: " + data));

        subject.onNext(2000);
        subject.doOnNext(data -> logger.info("소비자2 - onNext: " + data))
                .subscribe(data -> logger.info("소비자2 - data: " + data));

        subject.onNext(3000);
        subject.doOnNext(data -> logger.info("소비자3 - onNext: " + data))
                .subscribe(data -> logger.info("소비자3 - data: " + data));

        subject.onNext(4000);
        subject.onComplete();

        subject.doOnNext(data -> logger.info("소비자4 - onNext: " + data))
                .subscribe(data -> logger.info("소비자4 - data: " + data));

    }

    private static void behavior() {
        BehaviorSubject subject = BehaviorSubject.createDefault(3000);

        subject.subscribe(data -> logger.info("소비자1 - subscribe: " + data));
        subject.onNext(3100);

        subject.subscribe(data -> logger.info("소비자2 - subscribe: " + data));
        subject.onNext(3200);

        subject.subscribe(data -> logger.info("소비자3 - subscribe: " + data));
        subject.onNext(3300);
    }

    private static void replay() {

        ReplaySubject subject = ReplaySubject.create();
        subject.onNext(1000);
        subject.onNext(2000);

        subject.subscribe(data -> logger.info("소비자1 - subscribe: " + data));
        subject.onNext(3000);

        subject.subscribe(data -> logger.info("소비자2 - subscribe: " + data));
        subject.onNext(4000);

        subject.onComplete();
        subject.subscribe(data -> logger.info("소비자3 - subscribe: " + data));
    }

    private static void replay2() {

        ReplaySubject subject = ReplaySubject.createWithSize(2);
        subject.onNext(1000);
        subject.onNext(2000);

        subject.subscribe(data -> logger.info("소비자1 - subscribe: " + data));
        subject.onNext(3000);

        subject.subscribe(data -> logger.info("소비자2 - subscribe: " + data));
        subject.onNext(4000);

        subject.onComplete();
        subject.subscribe(data -> logger.info("소비자3 - subscribe: " + data));
    }
}
