package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter

public class SportCar extends Vehicle {
    private BigDecimal speed;
    private int year;
    private ColorSportCar colorSportCar;


    public SportCar(String model, BigDecimal price, Manufacturer manufacturer, RacingTires racingTires, BigDecimal speed, int year, ColorSportCar colorSportCar) {
        super(model, price, manufacturer, racingTires);
        this.speed = speed;
        this.year = year;
        this.colorSportCar = colorSportCar;
    }

    @Override
    public String toString() {
        return "SportCar{" +
                "speed=" + speed +
                ", year=" + year +
                ", colorSportCar=" + colorSportCar +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                ", racingTires=" + racingTires +
                '}';
    }
}