package org.example.service;

import org.example.model.*;
import org.example.reprository.SportCarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SportCarService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SportCarService.class);
    private static final Random RANDOM = new Random();
    private static final SportCarRepository SPORT_CAR_REPOSITORY = new SportCarRepository();

    public List<SportCar> createAuto(int count) {
        List<SportCar> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            double randomPriceValue = RANDOM.nextDouble() * 10000.0 + 1000.0; // Generate a random value from 1000 to 11000
            double randomSpeedValue = RANDOM.nextDouble() * 508.73;// Generate a random value from 0 to 508.73(max civil car speed)
            int randomYearValue = RANDOM.nextInt(2023 - 1901 + 1) + 1901;// Generate a random value from 1901 to 2023(1901 was first sport car)
            int randomModel = RANDOM.nextInt(1000);

            BigDecimal randomPrice = BigDecimal.valueOf(randomPriceValue).setScale(3, RoundingMode.HALF_UP);
            BigDecimal randomSpeed = BigDecimal.valueOf(randomSpeedValue).setScale(3, RoundingMode.HALF_UP);

            final SportCar auto = new SportCar(
                    "Model-" + randomModel,
                    randomPrice,
                    getRandomManufacturer(),
                    getRandomRacingTires(),
                    randomSpeed,
                    randomYearValue,
                    getRandomColorSportCar());
            result.add(auto);
            LOGGER.debug("Created auto {}", auto.getId());
        }
        return result;
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private Color getRandomColorSportCar() {
        final Color[] values = Color.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private RacingTires getRandomRacingTires() {
        final RacingTires[] values = RacingTires.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }


    public void saveAutos(List<SportCar> sportCars) {
        SPORT_CAR_REPOSITORY.saveAll(sportCars);

    }

    public void printAll() {
        for (SportCar sportCar : SPORT_CAR_REPOSITORY.getAll()) {
            System.out.println(sportCar);
        }
    }
}