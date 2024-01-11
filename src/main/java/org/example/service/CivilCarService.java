package org.example.service;

import org.example.model.CivilCar;
import org.example.model.FuelType;
import org.example.reprository.CrudRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class CivilCarService extends VehicleService<CivilCar> {
    public CivilCarService(CrudRepository<CivilCar> repository) {
        super(repository);
    }

    @Override
    protected CivilCar create() {
        double randomValue = ThreadLocalRandom.current().nextDouble(1000.0, 11000.0);
        BigDecimal randomBigDecimalPrice = BigDecimal.valueOf(randomValue).setScale(3, RoundingMode.HALF_UP);
        FuelType randomFuelType = getRandomFuelType(); //Getting a random fuel type value

        return new CivilCar(
                "Model-" + RANDOM.nextInt(1000),
                randomBigDecimalPrice,
                getRandomManufacturer(),
                getRandomRacingTires(),
                calculateFuelConsumption(randomFuelType), // Passing the fuel type to  calculate calculateFuelConsumption() method
                randomFuelType, 1);
    }

    protected FuelType getRandomFuelType() {
        final FuelType[] values = FuelType.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}