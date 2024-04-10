package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class CivilCar extends Vehicle {
    private double fuelConsumption;
    private FuelType fuelType;

    public CivilCar(String model, BigDecimal price, Manufacturer manufacturer, RacingTires racingTires, double fuelConsumption, FuelType fuelType, int count) {
        super(model, price, manufacturer, racingTires, count, VehicleType.CIVIL);
        this.fuelConsumption = fuelConsumption;
        this.fuelType = fuelType;
    }

    @Override
    public String toString() {
        return "CivilCar{" +
                "fuelConsumption=" + fuelConsumption +
                ", fuelType=" + fuelType +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                ", racingTires=" + racingTires +
                ", count=" + count +
                '}';
    }
}