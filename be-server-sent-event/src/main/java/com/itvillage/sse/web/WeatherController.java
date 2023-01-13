package com.itvillage.sse.web;

import com.itvillage.sse.domain.Weather;
import com.itvillage.sse.sensor.HumiditySensor;
import com.itvillage.sse.sensor.TemperatureSensor;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class WeatherController {

    final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;
    private final TemperatureSensor temperatureSensor;
    private final HumiditySensor humiditySensor;

    public WeatherController() {
        this.temperatureSensor = new TemperatureSensor();
        this.humiditySensor = new HumiditySensor();
    }

    private List<Disposable> disposables = new ArrayList<>();

    private SseEmitter sseEmitter;

    @CrossOrigin("*")
    @RequestMapping("/")
    public SseEmitter connectWeatherEvents() {
        sseEmitter = new SseEmitter(SSE_SESSION_TIMEOUT);

        ConnectableObservable<Weather> observable = Observable.zip(
                temperatureSensor.getTemperatureStream(),
                humiditySensor.getHumidityStream(),
                (temperature, humidity) -> new Weather(temperature, humidity)
        ).publish();

//        Disposable disposableSend = sendWeatherData(observable);
//        Disposable disposableSave = saveWeatherData(observable);
//        disposables.addAll(Arrays.asList(disposableSend, disposableSave));

        observable.connect();

        this.dispose(sseEmitter, () -> {
            disposables.stream()
                    .filter(disposable -> !disposable.isDisposed())
                    .forEach(Disposable::dispose);
        });

        return sseEmitter;
    }

    private void dispose(SseEmitter emitter, Runnable runnable) {
        emitter.onCompletion(runnable);
        emitter.onTimeout(runnable);
    }

}
