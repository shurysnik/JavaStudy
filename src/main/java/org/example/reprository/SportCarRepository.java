package org.example.reprository;

import org.example.model.SportCar;

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
    public boolean create(SportCar auto) {
        return sportCars.add(auto);
    }

    @Override
    public boolean create(List<SportCar> auto) {
        return sportCars.addAll(auto);
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

    @Override
    public boolean delete(String id) {
        return sportCars.removeIf(sportCar -> sportCar.getId().equals(id));
    }

    private static class SportCarCopy {
        static void copy(SportCar from, SportCar to) {
            to.setColorSportCar(from.getColorSportCar());
            to.setYear(from.getYear());
            to.setSpeed(from.getSpeed());
            to.setModel(from.getModel());
            to.setRacingTires(from.getRacingTires());
            to.setPrice(from.getPrice());
            to.setManufacturer(from.getManufacturer());
        }
    }
}