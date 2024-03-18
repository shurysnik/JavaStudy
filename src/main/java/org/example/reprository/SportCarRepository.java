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
    public SportCar getById(String id) {
        for (SportCar sportCar : sportCars) {
            if (sportCar.getId().equals(id)) {
                return sportCar;
            }
        }
        return null;
    }

    @Override
    public Optional<SportCar> findById(String id) {
        for (SportCar sportCar : sportCars) {
            if (sportCar.getId().equals(id)) {
                return Optional.of(sportCar);
            }
        }
        return Optional.empty();
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
    public boolean update(SportCar auto) {
        final SportCar founded = getById(auto.getId());
        if (founded != null) {
            SportCarCopy.copy(auto, founded);
            return true;
        }
        return false;
    }

    public boolean updateByYear(int year, SportCar copyFrom) {
        for (SportCar sportCar : sportCars) {
            if (sportCar.getYear() == year) {
                SportCarRepository.SportCarCopy.copy(copyFrom, sportCar);
            }
        }
        return true;
    }

    @Override
    public boolean deleteById(String id) {
        return sportCars.removeIf(sportCar -> sportCar.getId().equals(id));
    }

    @Override
    public boolean delete(SportCar sportCar) {
        return sportCars.remove(sportCar);
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