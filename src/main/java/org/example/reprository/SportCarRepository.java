package org.example.reprository;

import org.example.model.SportCar;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class SportCarRepository implements CrudRepository<SportCar> {
    private final List<SportCar> sportCars;

    public SportCarRepository() {
        sportCars = new LinkedList<>();
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
    public List<SportCar> getAll() {
        return sportCars;
    }

    @Override
    public boolean save(SportCar sportCar) {
        if (sportCar == null) {
            throw new IllegalArgumentException("Sport car must not be null");
        }
        if (sportCar.getPrice().equals(BigDecimal.ZERO)) {
            sportCar.setPrice(BigDecimal.valueOf(-1));
        }
        return sportCars.add(sportCar);
    }

    @Override
    public boolean saveAll(List<SportCar> sportCar) {
        if (sportCar == null) {
            throw new IllegalArgumentException("Sport car must not be null");
        }
        return sportCars.addAll(sportCar);
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
    public boolean delete(String id) {
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