package com.example.bbchu.rxjava.publisher;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 *
 * Single: 데이터 건수 1
 * Maybe: 데이터 건수 1 or 0
 * Completable: 데이터 건수 0
 *
 */
public class SingleExample {

    public static void main(String[] args) {

//        normal();
        lambda();

    }

    private static void normal() {
        Single<String> single = Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> emitter) throws Exception {
                emitter.onSuccess("success");
            }
        });

        single.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                // do nothing
            }

            @Override
            public void onSuccess(@NonNull String s) {
                success(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                error(e);
            }
        });

    }

    private static void lambda() {
        Single<String> single = Single.create(emitter -> emitter.onSuccess("hello world"));

        single.subscribe(
                data -> success(data),
                error -> error(error)
        );
    }

    private static void success(Object data) {
        System.out.println("data: " + data);
    }

    private static void error(Object error) {
        System.out.println("error: " + error);
    }
}
