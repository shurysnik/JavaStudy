package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class Auto extends Vehicle {
    private String bodyType;

    public Auto(String model, BigDecimal price, Manufacturer manufacturer, RacingTires racingTires, String bodyType, int count) {
        super(model, price, manufacturer, racingTires, count, VehicleType.AUTO);
        this.bodyType = bodyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auto auto = (Auto) o;
        return Objects.equals(bodyType, auto.bodyType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bodyType);
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
                ", count=" + count +
                '}';
    }


}