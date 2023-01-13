package com.example.bbchu.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CarFilter {

    public static List<Car> filterCarByCustomPredicate(List<Car> cars, CarPredicate p) {
        List<Car> resultCars = new ArrayList<>();
        for (Car car : cars) {
            if (p.test(car)) {
                resultCars.add(car);
            }
        }

        return resultCars;
    }

    public static List<Car> filterCarByBuiltinPredicate(List<Car> cars, Predicate<Car> p) {
        List<Car> resultCars = new ArrayList<>();
        for (Car car : cars) {
            if (p.test(car)) {
                resultCars.add(car);
            }
        }

        return resultCars;
    }
}
