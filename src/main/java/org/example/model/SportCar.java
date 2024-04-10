package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class SportCar extends Vehicle {
    private BigDecimal speed;
    private int year;
    private Color color;

    public SportCar(String model, BigDecimal price, Manufacturer manufacturer, RacingTires racingTires, BigDecimal speed, int year, Color color, int count) {
        super(model, price, manufacturer, racingTires, count, VehicleType.SPORT);
        this.speed = speed;
        this.year = year;
        this.color = color;
    }

    @Override
    public String toString() {
        return "SportCar{" +
                "speed=" + speed +
                ", year=" + year +
                ", color=" + color +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                ", racingTires=" + racingTires +
                ", count=" + count +
                '}';
    }
}