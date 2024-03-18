package org.example.service;

import org.example.model.Color;
import org.example.model.SportCar;
import org.example.reprository.CrudRepository;
import org.example.reprository.SportCarRepository;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

public class SportCarService extends VehicleService<SportCar> {

    private static SportCarService instance;
    BigDecimal randomValue = roundValue(generateRandomValues(1000.0, 11000.0));
    BigDecimal randomSpeed = roundValue(generateRandomValues(0, 508.73));
    int randomYearValue = generateRandomInt(1901, 2023);
    int randomModel = generateRandomInt(0, 1000);
    private SportCarService(CrudRepository<SportCar> repository) {
        super(repository);
    }

    public static SportCarService getInstance() {
        if (instance == null) {
            instance = new SportCarService(SportCarRepository.getInstance());
        }
        return instance;
    }

    protected static BigDecimal generateRandomValues(double minValue, double maxValue) {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(minValue, maxValue));
    }

    protected static int generateRandomInt(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

    @Override
    public SportCar create() {

        return new SportCar(
                "Model-" + randomModel,
                randomValue,
                getRandomManufacturer(),
                getRandomRacingTires(),
                randomSpeed,
                randomYearValue,
                getRandomColor(), 1);
    }

    protected Color getRandomColor() {
        final Color[] values = Color.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public SportCar createRandomVehicle() {
        String model = "new Model_" + RANDOM.nextInt(10);
        return new SportCar(model,
                generateRandomValues(1, 10000), getRandomManufacturer(), getRandomRacingTires(), randomSpeed, randomYearValue, getRandomColor(), randomModel);
    }
}