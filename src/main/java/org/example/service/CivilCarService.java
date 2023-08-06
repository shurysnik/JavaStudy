package org.example.service;

import org.example.model.CivilCar;
import org.example.model.FuelType;
import org.example.model.Manufacturer;
import org.example.model.RacingTires;
import org.example.reprository.CivilCarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CivilCarService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CivilCarService.class);
    private static final Random RANDOM = new Random();
    private final CivilCarRepository civilCarRepository;

    public CivilCarService(CivilCarRepository civilCarRepository) {
        this.civilCarRepository = civilCarRepository;
    }

    public List<CivilCar> createAndSaveAutos(int count) {
        List<CivilCar> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {

            double randomValue = ThreadLocalRandom.current().nextDouble(1000.0, 11000.0);
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
            civilCarRepository.save(auto);
            LOGGER.debug("Created auto {}", auto.getId());
        }
        return result;
    }

    public CivilCar findById(String id) {
        return civilCarRepository.getById(Objects.requireNonNullElse(id, ""));
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

    public CivilCar update(CivilCar civilCar) {
        if (civilCar == null) {
            throw new IllegalArgumentException("Civil auto must be not null");
        }
        civilCar = new CivilCar(
                "Model",
                BigDecimal.ONE,
                Manufacturer.MAZDA,
                RacingTires.SLICKS
                , 0.0, FuelType.PETROL);
        civilCarRepository.update(civilCar);
        LOGGER.info("New Auto {}", civilCar);
        civilCarRepository.save(civilCar);
        return civilCar;
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

    public boolean saveAutos(List<CivilCar> savedCivilCars) {
        return civilCarRepository.saveAll(savedCivilCars);
    }

    public boolean saveAuto(CivilCar savedCivilCar) {
        return civilCarRepository.save(savedCivilCar);
    }

    public boolean delete(CivilCar civilCar) {
        return civilCarRepository.delete(civilCar);
    }

    public boolean deleteById(String id) {
        return civilCarRepository.deleteById(id);
    }

    public void printAll() {
        for (CivilCar civilCar : civilCarRepository.getAll()) {
            System.out.println(civilCar);
        }
    }
}