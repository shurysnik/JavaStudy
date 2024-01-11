package org.example;

import org.example.model.Vehicle;

import java.math.BigDecimal;
import java.util.Random;

public class Container<T extends Vehicle> {
    private T car;

    public Container(T car) {
        this.car = car;
    }

    public T getCar() {
        return car;
    }

    public void generateRandomDiscount() {
        BigDecimal discount = BigDecimal.valueOf(new Random().nextDouble(0.1, 0.3));
        System.out.println("My discount is " + discount);
        car.setPrice(car.getPrice().multiply(BigDecimal.ONE.subtract(discount)));
    }

    public void increasePrice(Number value) {
        double doubleValue = value.doubleValue();
        if (doubleValue < 0) {
            throw new IllegalArgumentException("Auto cant be less than 0");
        }
        car.setPrice(car.getPrice().add(BigDecimal.valueOf(doubleValue)));
    }

    @Override
    public String toString() {
        return "Container{" +
                "car=" + car +
                '}';
    }
}
