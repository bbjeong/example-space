package com.example.bbchu.lambda;

import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        List<Car> cars = Arrays.asList(
                new Car(100),
                new Car(200),
                new Car(300),
                new Car(400)
        );

        List<Car> carsFilterByPrice = CarFilter.filterCarByCustomPredicate(cars, new CarPredicate() {
            @Override
            public boolean test(Car car) {
                return car.getCarPrice() > 200;
            }
        });
        print(carsFilterByPrice);

        List<Car> carsFilterByPrice2 = CarFilter.filterCarByBuiltinPredicate(cars, (Car car) -> car.getCarPrice() > 300);
        print(carsFilterByPrice2);

    }

    private static void print(List<Car> cars) {
        for (Car car : cars) {
            System.out.println(car.getCarPrice());
        }
    }
}
