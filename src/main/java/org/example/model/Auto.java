package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Auto extends Vehicle {
    private String bodyType;

    public Auto(String model, BigDecimal price, Manufacturer manufacturer, RacingTires racingTires, String bodyType) {
        super(model, price, manufacturer, racingTires);
        this.bodyType = bodyType;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "bodyType='" + bodyType + '\'' +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                ", racingTires=" + racingTires +
                '}';
    }
}