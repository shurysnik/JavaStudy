package org.example.service;

import org.example.model.Auto;
import org.example.model.Manufacturer;
import org.example.model.RacingTires;
import org.example.reprository.AutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class AutoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private static final Random RANDOM = new Random();
    private final AutoRepository autoRepository;
    private static final String MASSAGE = "I don't found auto with this id";

    public Auto createSimpleAuto() {
        return new Auto("Model-", BigDecimal.ZERO, Manufacturer.HYUNDAI, RacingTires.RACING, "Type");
    }


    public AutoService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    public List<Auto> createAndSaveAutos(int count) {
        List<Auto> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            double randomValue = ThreadLocalRandom.current().nextDouble(1000.0, 11000.0); // Generate a random value from 1000 to 11000
            BigDecimal randomPrice = BigDecimal.valueOf(randomValue).setScale(3, RoundingMode.HALF_UP);
            int randomIndex = RANDOM.nextInt(1000);
            final Auto auto = new Auto("Model-" + randomIndex, randomPrice, getRandomManufacturer(), getRandomRacingTires(), "Model-" + randomIndex);
            result.add(auto);
            autoRepository.save(auto);
            LOGGER.debug("Created auto {}", auto.getId());
        }
        return result;
    }

    public Auto findOneById(String id) {
        return autoRepository.getById(Objects.requireNonNullElse(id, ""));
    }

    public Auto update(Auto auto) {
        if (auto == null) {
            throw new IllegalArgumentException("Auto must be not null");
        }
        auto = new Auto("Model", BigDecimal.ONE, Manufacturer.MAZDA, RacingTires.SLICKS, "Model");
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

    public boolean saveAutos(List<Auto> savedAutos) {
        return autoRepository.saveAll(savedAutos);
    }

    public boolean saveAuto(Auto savedAuto) {
        return autoRepository.save(savedAuto);
    }

    public void printAll() {
        for (Auto auto : autoRepository.getAll()) {
            System.out.println(auto);
        }
    }

    public void optionalExamples() {
        final Auto auto = createAndSaveAutos(1).get(0);
        final String autoId = auto.getId();
        ifPresent(autoId);
        orElse(autoId);
        orElseGet(autoId);
        map(autoId);
        ifPresentOrElse(autoId);
        filter(autoId);
        orElseThrow(autoId);
        or(autoId);
    }

    public void ifPresent(String id) {
        autoRepository.findById(id).ifPresent(auto -> System.out.println("My auto has id" + id));
    }

    public Auto orElse(String id) {
        return autoRepository.findById(id).orElse(createSimpleAuto());
    }

    public Auto orElseGet(String id) {
        Auto foundAuto = autoRepository.findById(id).orElseGet(() -> {
            System.out.println("We create new auto");
            String newModel = "newModel";
            System.out.println("Auto has new model " + newModel);
            return new Auto(newModel, BigDecimal.ZERO, Manufacturer.HYUNDAI, RacingTires.RACING, "Type");
        });
        System.out.println("Auto with this id " + id + " was successfully created ");
        return foundAuto;
    }

    public void map(String id) {
        autoRepository.findById(id).map(auto -> auto.getModel().toUpperCase())
                .ifPresentOrElse(model -> System.out.println("model" + model),
                        () -> System.out.println(MASSAGE + id));
    }

    public void ifPresentOrElse(String id) {
        autoRepository.findById(id).ifPresentOrElse(auto -> System.out.println("our auto has model" + auto.getModel())
                , () -> System.out.println(MASSAGE + id));
    }

    public Auto orElseThrow(String id) {
        Auto auto = autoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(MASSAGE + id));
        return auto;
    }

    public void or(String id) {
        autoRepository.findById(id).or(() -> Optional.of(createSimpleAuto()));
    }

    public void filter(String id) {
        autoRepository.findById(id).filter(auto -> auto.getBodyType().startsWith("T"))
                .ifPresentOrElse(auto -> System.out.println("My bodyType" + auto.getBodyType()),
                        () -> System.out.println(MASSAGE + id));
    }

}
