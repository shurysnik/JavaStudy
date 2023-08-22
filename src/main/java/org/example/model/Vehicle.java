package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
public abstract class Vehicle {
    protected final String id;
    protected String model;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id) && Objects.equals(model, vehicle.model) && Objects.equals(price, vehicle.price) && manufacturer == vehicle.manufacturer && racingTires == vehicle.racingTires;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, price, manufacturer, racingTires);
    }

    protected BigDecimal price;
    protected Manufacturer manufacturer;
    protected RacingTires racingTires;

    protected Vehicle(String model, BigDecimal price, Manufacturer manufacturer, RacingTires racingTires) {
        this.id = UUID.randomUUID().toString();
        this.model = model;
        this.price = price;
        this.manufacturer = manufacturer;
        this.racingTires = racingTires;
    }
}