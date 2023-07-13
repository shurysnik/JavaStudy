package org.example.reprository;

import org.example.model.Auto;
import org.example.model.CivilCar;

import java.util.LinkedList;
import java.util.List;

public class CivilCarRepository implements CrudRepository<CivilCar> {
    public final List<CivilCar> civilCars;

    public CivilCarRepository() {
        civilCars = new LinkedList<>();
    }

    @Override
    public CivilCar getById(String id) {
        for (CivilCar civilCar : civilCars) {
            if (civilCar.getId().equals(id)) {
                return civilCar;
            }
        }
        return null;
    }

    @Override
    public List<CivilCar> getAll() {
        return civilCars;
    }

    @Override
    public boolean create(CivilCar auto) {
        return civilCars.add(auto);
    }

    @Override
    public boolean createAll(List<CivilCar> auto) {
        return civilCars.addAll(auto);
    }

    @Override
    public boolean update(CivilCar auto) {
        final CivilCar founded = getById(auto.getId());
        if (founded != null) {
            CivilCarCopy.copy(auto, founded);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        return civilCars.removeIf(auto -> auto.getId().equals(id));
    }
    @Override
    public boolean deleteAuto(CivilCar auto) {
        return civilCars.remove(auto);
    }
    private static class CivilCarCopy {
        static void copy(CivilCar from, CivilCar to) {
            to.setFuelConsumption(from.getFuelConsumption());
            to.setFuelType(from.getFuelType());
            to.setManufacturer(from.getManufacturer());
            to.setModel(from.getModel());
            to.setPrice(from.getPrice());
            to.setRacingTires(from.getRacingTires());
        }
    }
}