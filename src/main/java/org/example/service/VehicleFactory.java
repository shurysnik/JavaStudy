package org.example.service;

import org.example.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class VehicleFactory {
    private static final Random RANDOM = new Random();
    private static final Scanner SCANNER = new Scanner(System.in);
    private static VehicleFactory instance;
    double randomValue = ThreadLocalRandom.current().nextDouble(1000.0, 11000.0); // Generate a random value from 1000 to 11000
    BigDecimal randomPrice = BigDecimal.valueOf(randomValue).setScale(3, RoundingMode.HALF_UP);
    int randomIndex = RANDOM.nextInt(1000);
    BigDecimal randomBigDecimalPrice = BigDecimal.valueOf(randomValue).setScale(3, RoundingMode.HALF_UP);
    FuelType randomFuelType = getRandomFuelType(); //Getting a random fuel type value
    int randomYearValue = generateRandomInt(1901, 2023);
    int randomModel = generateRandomInt(0, 1000);
    int minValue = SCANNER.nextInt();
    BigDecimal randomSpeed = roundValue(generateRandomValues(minValue));
    String model = "Model- ";

    private VehicleFactory() {
    }

    public static VehicleFactory getInstance() {
        if (instance == null) {
            instance = new VehicleFactory();
        }
        return instance;
    }

    protected static BigDecimal generateRandomValues(double minValue) {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(minValue, 508.73));
    }

    protected static int generateRandomInt(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

    protected static BigDecimal roundValue(BigDecimal value) {
        return value.setScale(3, RoundingMode.HALF_UP);
    }

    protected Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    protected RacingTires getRandomRacingTires() {
        final RacingTires[] values = RacingTires.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    protected double calculateFuelConsumption(FuelType fuelType) {
        return switch (fuelType) {
            case PETROL -> generateRandomValue(6.0, 12.0);
            case DIESEL -> generateRandomValue(0.9, 1.8);
            case HYBRID -> generateRandomValue(1.5, 3.5);
        };
    }

    protected FuelType getRandomFuelType() {
        final FuelType[] values = FuelType.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    protected double generateRandomValue(double min, double max) {
        return Math.round((RANDOM.nextDouble() * (max - min) + min) * 1000.0) / 1000.0;
    }

    protected Color getRandomColor() {
        final Color[] values = Color.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public Vehicle build(VehicleType type) {
        return switch (type) {
            case AUTO -> new Auto(
                    model + randomIndex, randomPrice, getRandomManufacturer(),
                    getRandomRacingTires(), model + randomIndex, 1
            );
            case CIVIL -> new CivilCar(
                    model + RANDOM.nextInt(1000),
                    randomBigDecimalPrice,
                    getRandomManufacturer(),
                    getRandomRacingTires(),
                    calculateFuelConsumption(randomFuelType), // Passing the fuel type to  calculate calculateFuelConsumption() method
                    randomFuelType, 1
            );
            case SPORT -> new SportCar(
                    "Model-" + randomModel,
                    BigDecimal.valueOf(randomValue),
                    getRandomManufacturer(),
                    getRandomRacingTires(),
                    randomSpeed,
                    randomYearValue,
                    getRandomColor(), 1
            );
        };
    }
}
