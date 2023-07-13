package org.example.service;

import org.example.model.*;
import org.example.reprository.CivilCarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CivilCarService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CivilCarService.class);
    private static final Random RANDOM = new Random();
    private static final CivilCarRepository CIVIL_CAR_REPOSITORY = new CivilCarRepository();

    public List<CivilCar> createAuto(int count) {
        List<CivilCar> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            double randomValue = RANDOM.nextDouble() * 10000.0 + 1000.0;
            BigDecimal randomBigDecimalPrice = BigDecimal.valueOf(randomValue).setScale(3, RoundingMode.HALF_UP);
            int randomModel = RANDOM.nextInt(1000);
            FuelType randomFuelType = getRandomFuelType(); //Getting a random fuel type value

            final CivilCar auto = new CivilCar(
                    "Model-" + randomModel,
                    randomBigDecimalPrice,
                    getRandomManufacturer(),
                    getRandomRacingTires(),
                    calculateFuelConsumption(randomFuelType), // Passing the fuel type to  calculate calculateFuelConsumption() method
                    randomFuelType);
            result.add(auto);
            LOGGER.debug("Created auto {}", auto.getId());
        }
        return result;
    }

    private FuelType getRandomFuelType() {
        final FuelType[] values = FuelType.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public double calculateFuelConsumption(FuelType fuelType) {
        double fuelConsumption = switch (fuelType) {
            case PETROL -> generateRandomValue(6.0, 12.0);
            case DIESEL -> generateRandomValue(0.9, 1.8);
            case HYBRID -> generateRandomValue(1.5, 3.5);
        };

        LOGGER.info("Fuel type {}", fuelType);
        return fuelConsumption;
    }

    private double generateRandomValue(double min, double max) {
        return Math.round((RANDOM.nextDouble() * (max - min) + min) * 1000.0) / 1000.0;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private RacingTires getRandomRacingTires() {
        final RacingTires[] values = RacingTires.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveAutos(List<CivilCar> civilCars) {
        CIVIL_CAR_REPOSITORY.createAll(civilCars);
    }

    public void printAll() {
        for (CivilCar civilCar : CIVIL_CAR_REPOSITORY.getAll()) {
            LOGGER.info("written data {}", civilCar);
        }
    }
}