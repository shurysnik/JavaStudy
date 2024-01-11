package org.example.service;

import org.example.model.Auto;
import org.example.reprository.CrudRepository;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class AutoService extends VehicleService<Auto> {

    public AutoService(CrudRepository<Auto> repository) {
        super(repository);
    }

    @Override
    protected Auto create() {
        double randomValue = ThreadLocalRandom.current().nextDouble(1000.0, 11000.0); // Generate a random value from 1000 to 11000
        BigDecimal randomPrice = BigDecimal.valueOf(randomValue).setScale(3, RoundingMode.HALF_UP);
        int randomIndex = RANDOM.nextInt(1000);
        return new Auto("Model-" + randomIndex, randomPrice, getRandomManufacturer(), getRandomRacingTires(), "Model-" + randomIndex, 1);
    }
    public void getTotalSumOf(String id) {
        repository.findById(id).ifPresent(vehicle -> {
            final int count = vehicle.getCount();
            final BigDecimal price = vehicle.getPrice();
            final int totalSum = count * price.intValue();
            System.out.printf("Auto %s has total sum %d%n", vehicle.getModel(), totalSum);
        });
    }
}
