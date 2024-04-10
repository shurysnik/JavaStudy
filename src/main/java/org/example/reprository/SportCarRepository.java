package org.example.reprository;

import org.example.model.SportCar;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SportCarRepository implements CrudRepository<SportCar> {
    public static SportCarRepository instance;
    private final List<SportCar> sportCars;


    private SportCarRepository() {
        sportCars = new LinkedList<>();
    }

    public static SportCarRepository getInstance() {
        if (instance == null) {
            instance = new SportCarRepository();
        }
        return instance;
    }

    @Override
    public Optional<SportCar> findById(String id) {
        return sportCars.stream()
                .filter(sportCar -> sportCar.getId().equals(id))
                .findAny();
    }

    @Override
    public List<SportCar> getAll() {
        return sportCars;
    }

    @Override
    public boolean save(SportCar savedSportCar) {
        if (savedSportCar == null) {
            throw new IllegalArgumentException("Sport car must not be null");
        }
        if (savedSportCar.getPrice().equals(BigDecimal.ZERO)) {
            savedSportCar.setPrice(BigDecimal.valueOf(-1));
        }
        return sportCars.add(savedSportCar);
    }

    @Override
    public boolean saveAll(List<SportCar> savedSportCars) {
        if (savedSportCars == null) {
            throw new IllegalArgumentException("Sport car must not be null");
        }
        return sportCars.addAll(savedSportCars);
    }

    @Override
    public void update(SportCar auto) {
        Optional<SportCar> optionalAuto = findById(auto.getId());
        optionalAuto.ifPresentOrElse(founded -> SportCarCopy.copy(auto, founded),
                () -> save(auto));
    }

    @Override
    public boolean deleteById(String id) {
        return sportCars.removeIf(sportCar -> sportCar.getId().equals(id));
    }

    @Override
    public boolean delete(SportCar sportCar) {
        return sportCars.remove(sportCar);
    }

    public void resetForTest() {
        sportCars.clear();
    }

    private static class SportCarCopy {
        static void copy(SportCar from, SportCar to) {
            to.setColor(from.getColor());
            to.setYear(from.getYear());
            to.setSpeed(from.getSpeed());
            to.setModel(from.getModel());
            to.setRacingTires(from.getRacingTires());
            to.setPrice(from.getPrice());
        }
    }
}