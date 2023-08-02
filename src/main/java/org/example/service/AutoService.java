package org.example.service;

import org.example.model.Auto;
import org.example.model.Manufacturer;
import org.example.model.RacingTires;
import org.example.reprository.AutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AutoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private static final Random RANDOM = new Random();
    private final AutoRepository autoRepository;

    public AutoService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    public List<Auto> createAndSaveAutos(int count) {
        List<Auto> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            double randomValue = RANDOM.nextDouble() * 10000.0 + 1000.0; // Generate a random value from 1000 to 11000
            BigDecimal randomPrice = BigDecimal.valueOf(randomValue).setScale(3, RoundingMode.HALF_UP);
            int randomModel = RANDOM.nextInt(1000);
            int randomBodyType = RANDOM.nextInt(1000);

            final Auto auto = new Auto(
                    "Model-" + randomModel,
                    randomPrice,
                    getRandomManufacturer(),
                    getRandomRacingTires(),
                    "Model-" + randomBodyType);
            result.add(auto);
            autoRepository.save(auto);
            LOGGER.debug("Created auto {}", auto.getId());
        }
        return result;
    }

    public Auto findOneById(String id) {
        if (id == null) {
            return autoRepository.getById("");
        } else {
            return autoRepository.getById(id);
        }
    }

    public Auto update(Auto auto) {
        if (auto == null) {
            throw new IllegalArgumentException("Auto must be not null");
        }
        auto = new Auto(
                "Model",
                BigDecimal.ONE,
                Manufacturer.MAZDA,
                RacingTires.SLICKS,
                "Model");
        autoRepository.update(auto);
        LOGGER.info("New Auto {}", auto);
        autoRepository.save(auto);
        return auto;
    }

    public boolean deleteById(String auto) {
        autoRepository.deleteById(auto);
        LOGGER.info("Delete Auto {}", auto);
        return autoRepository.deleteById(auto);
    }

    public boolean delete(Auto auto) {
        return autoRepository.delete(auto);
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

    public boolean saveAutos(List<Auto> autos) {
        return autoRepository.saveAll(autos);
    }

    public boolean saveAuto(Auto auto) {
        return autoRepository.save(auto);
    }

    public void printAll() {
        for (Auto auto : autoRepository.getAll()) {
            System.out.println(auto);
        }
    }
}