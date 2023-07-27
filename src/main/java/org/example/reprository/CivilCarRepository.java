package org.example.reprository;

import org.example.model.Auto;
import org.example.model.CivilCar;

import java.math.BigDecimal;
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
    public boolean save(CivilCar civilCar) {
        if (civilCar == null) {
            throw new IllegalArgumentException("Civil auto must not be null");
        }
        if (civilCar.getPrice().equals(BigDecimal.ZERO)) {
            civilCar.setPrice(BigDecimal.valueOf(-1));
        }
        return civilCars.add(civilCar);
    }

    @Override
    public boolean saveAll(List<CivilCar> civilCar) {
        if (civilCar == null) {
            throw new IllegalArgumentException("Civil autos must not be null");
        }
        return civilCars.addAll(civilCar);
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

    public boolean updateByModel(String model, CivilCar copyFrom) {
        for (CivilCar civilCar : civilCars) {
            if (civilCar.getModel().equals(model)) {
                CivilCarRepository.CivilCarCopy.copy(copyFrom, civilCar);
            }
        }
        return true;
    }

    @Override
    public boolean delete(String id) {
        return civilCars.removeIf(auto -> auto.getId().equals(id));
    }

    @Override
    public boolean delete(CivilCar civilCar) {
        return civilCars.remove(civilCar);
    }

    private static class CivilCarCopy {
        static void copy(CivilCar from, CivilCar to) {
            to.setFuelConsumption(from.getFuelConsumption());
            to.setFuelType(from.getFuelType());
            to.setModel(from.getModel());
            to.setPrice(from.getPrice());
            to.setRacingTires(from.getRacingTires());
        }
    }
}