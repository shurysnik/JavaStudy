package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public abstract class Vehicle {
    protected final String id;
    protected String model;
    protected BigDecimal price;
    protected Manufacturer manufacturer;
    protected RacingTires racingTires;
    protected int count;
    protected VehicleType type;
    //protected List<String> details;

    protected Vehicle(String model, BigDecimal price, Manufacturer manufacturer, RacingTires racingTires, int count, VehicleType type) {
        this.id = UUID.randomUUID().toString();
        this.model = model;
        this.price = price;
        this.manufacturer = manufacturer;
        this.racingTires = racingTires;
        this.count = count;
        this.type = type;
     //   this.details = details;
    }
}