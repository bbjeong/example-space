package com.itvillage.sse.sensor;

import com.itvillage.sse.utils.NumberUtil;
import io.reactivex.Observable;
import org.springframework.util.NumberUtils;

import java.util.concurrent.TimeUnit;

public class HumiditySensor {

    public Observable<Integer> getHumidityStream() {
                return Observable.interval(0L, TimeUnit.MILLISECONDS)
                        .delay(item -> {
                            Thread.sleep(NumberUtil.randomRange(1000, 3000));
                            return Observable.just(item);
                        }).map(notUse -> this.getHumidity());
    }

    private int getHumidity() {
        return NumberUtil.randomRange(30, 70);
    }

}
