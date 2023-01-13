package com.itvillage.sse.sensor;

import com.itvillage.sse.utils.NumberUtil;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class TemperatureSensor {

    public Observable<Integer> getTemperatureStream() {
        return Observable.interval(0L, TimeUnit.MILLISECONDS)
                .delay(item -> {
                    Thread.sleep(NumberUtil.randomRange(1000, 3000));
                    return Observable.just(item);
                }).map(notUse -> this.getTemperature());
    }

    private int getTemperature() {
        return NumberUtil.randomRange(-10, 30);
    }
}
