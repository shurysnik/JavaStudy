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
    private static final AutoRepository AUTO_REPOSITORY = new AutoRepository();


    public List<Auto> createAuto(int count) {
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
            LOGGER.debug("Created auto {}", auto.getId());
        }
        return result;
    }

    public Auto update(Auto auto) {
        auto = new Auto(
                "Model-" + 1,
                BigDecimal.ONE,
                Manufacturer.MAZDA,
                RacingTires.SLICKS,
                "Model-" + 1);
        AUTO_REPOSITORY.update(auto);
        LOGGER.info("New Auto {}",auto);
        return auto;
    }

    public boolean delete(String auto) {
           AUTO_REPOSITORY.delete(auto );
        LOGGER.info("Delete Auto {}",auto);
        return AUTO_REPOSITORY.delete(auto );
    }
    public boolean deleteAuto(Auto auto) {
        return AUTO_REPOSITORY.deleteAuto(auto);
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


    public void saveAutos(List<Auto> autos) {
        AUTO_REPOSITORY.createAll(autos);
    }

    public void saveAuto(Auto auto) {
        AUTO_REPOSITORY.create(auto);
    }

    public void printAll() {
        for (Auto auto : AUTO_REPOSITORY.getAll()) {
            System.out.println(auto);
            // LOGGER.info("written data {}", auto);
        }
    }

}