package com.itvillage.sse.domain;

public class Weather {

    private int humidity;
    private int temperature;

    public Weather(int temperature, int humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
