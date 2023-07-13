package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public abstract class Vehicle {
    protected final String id;
    protected String model;
    protected BigDecimal price;
    protected Manufacturer manufacturer;

    protected Vehicle(String model, BigDecimal price, Manufacturer manufacturer) {
        this.id = UUID.randomUUID().toString();
        this.model = model;
        this.price = price;
        this.manufacturer = manufacturer;
    }


}
