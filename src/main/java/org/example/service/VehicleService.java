package org.example.service;

import org.example.model.*;
import org.example.reprository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public abstract class VehicleService<T extends Vehicle> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);
    protected static final Random RANDOM = new Random();
    protected final CrudRepository<T> repository;

    protected VehicleService(CrudRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> createAndSaveAutos(int count) {
        List<T> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T vehicle = create();
            result.add(vehicle);
            repository.save(vehicle);
            LOGGER.debug("Created vehicle {}", vehicle.getId());
        }
        return result;
    }

    protected abstract T create();

    public T findOneById(String id) {
        return repository.getById(Objects.requireNonNullElse(id, ""));
    }

    protected Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public boolean update(T vehicle) {
        if (vehicle.getPrice().equals(BigDecimal.ZERO)) {
            vehicle.setPrice(BigDecimal.valueOf(-1));
        }
        if (vehicle.getManufacturer() == null) {
            throw new IllegalArgumentException("Vehicle cant be null");
        }
        return repository.update(vehicle);
    }

    public boolean deleteById(String vehicle) {
        repository.deleteById(vehicle);
        LOGGER.info("Delete Vehicle {}", vehicle);
        return repository.deleteById(vehicle);
    }

    public boolean delete(T vehicle) {
        return repository.delete(vehicle);
    }


    public boolean save(List<T> savedVehicles) {
        return repository.saveAll(savedVehicles);
    }

    public boolean save(T savedVehicle) {
        return repository.save(savedVehicle);
    }

    protected static BigDecimal roundValue(BigDecimal value) {
        return value.setScale(3, RoundingMode.HALF_UP);
    }

    protected double generateRandomValue(double min, double max) {
        return Math.round((RANDOM.nextDouble() * (max - min) + min) * 1000.0) / 1000.0;
    }

    protected double calculateFuelConsumption(FuelType fuelType) {
        return switch (fuelType) {
            case PETROL -> generateRandomValue(6.0, 12.0);
            case DIESEL -> generateRandomValue(0.9, 1.8);
            case HYBRID -> generateRandomValue(1.5, 3.5);
        };
    }

    protected RacingTires getRandomRacingTires() {
        final RacingTires[] values = RacingTires.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void printAll() {
        for (T vehicle : repository.getAll()) {
            System.out.println(vehicle);
        }
    }
}
