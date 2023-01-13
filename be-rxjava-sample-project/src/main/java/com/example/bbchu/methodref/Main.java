package com.example.bbchu.methodref;

import com.example.bbchu.lambda.Car;

import java.util.function.Function;
import java.util.function.Supplier;

public class Main {


    public static void main(String[] args) {
        Car car = new Car(1000);
        Function<Car, Integer> f1 = Car::getCarPrice;

        int r1 = f1.apply(car);
        System.out.println(r1);

        Car car2 = new Car(2000);
        Supplier<Integer> s1 = car2::getCarPrice;

        int r2 = s1.get();
        System.out.println(r2);

        Function<Integer, Car> f2 = Car::new;
        int r3 = f2.apply(3000).getCarPrice();
        System.out.println(r3);

    }

}
