package org.example.service;

import org.example.model.Color;
import org.example.model.Manufacturer;
import org.example.model.RacingTires;
import org.example.model.SportCar;
import org.example.reprository.SportCarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SportCarService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SportCarService.class);
    private static final Random RANDOM = new Random();
    private final SportCarRepository sportCarRepository;

    public SportCarService(SportCarRepository sportCarRepository) {
        this.sportCarRepository = sportCarRepository;
    }

    private static BigDecimal generateRandomValue(double minValue, double maxValue) {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(minValue, maxValue));
    }

    private static int generateRandomInt(int minValue, int maxValue) {
        return ThreadLocalRandom.current().nextInt(minValue, maxValue);
    }

    private static BigDecimal roundValue(BigDecimal value) {
        return value.setScale(3, RoundingMode.HALF_UP);
    }

    public List<SportCar> createAndSaveAutos(int count) {
        List<SportCar> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            int randomYearValue = generateRandomInt(1901, 2023);
            int randomModel = generateRandomInt(0, 1000);
            BigDecimal randomValue = roundValue(generateRandomValue(1000.0, 11000.0));
            BigDecimal randomSpeed = roundValue(generateRandomValue(0, 508.73));
            final SportCar auto = new SportCar(
                    "Model-" + randomModel,
                    randomValue,
                    getRandomManufacturer(),
                    getRandomRacingTires(),
                    randomSpeed,
                    randomYearValue,
                    getRandomColorSportCar());
            result.add(auto);
            sportCarRepository.save(auto);
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

    public boolean delete(SportCar sportCar) {
        return sportCarRepository.delete(sportCar);
    }

    public boolean deleteById(String id) {
        return sportCarRepository.deleteById(id);
    }

    public boolean saveAutos(List<SportCar> sportCars) {
        if (sportCars == null) {
            throw new IllegalArgumentException("Sport car must not be null");
        }
        return sportCarRepository.saveAll(sportCars);
    }

    public SportCar update(SportCar sportCar) {
        if (sportCar == null) {
            throw new IllegalArgumentException("Sport car must be not null");
        }
        sportCar = new SportCar(
                "Model",
                BigDecimal.ONE,
                Manufacturer.MAZDA,
                RacingTires.SLICKS,
                BigDecimal.ONE,
                2010, Color.YELLOW);

        sportCarRepository.update(sportCar);
        LOGGER.info("New Auto {}", sportCar);
        sportCarRepository.save(sportCar);
        return sportCar;
    }

    public boolean saveAuto(SportCar sportCar) {
        if (sportCar == null) {
            throw new IllegalArgumentException("Sport car must not be null");
        }
        if (sportCar.getPrice().equals(BigDecimal.ZERO)) {
            sportCar.setPrice(BigDecimal.valueOf(-1));
        }
        return sportCarRepository.save(sportCar);
    }

    public SportCar findOneById(String id) {
        return sportCarRepository.getById(Objects.requireNonNullElse(id, ""));
    }

    public void printAll() {
        for (SportCar sportCar : sportCarRepository.getAll()) {
            System.out.println(sportCar + " ");
        }
    }
}