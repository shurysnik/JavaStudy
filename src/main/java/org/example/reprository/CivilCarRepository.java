package org.example.reprository;

import org.example.model.CivilCar;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
    public Optional<CivilCar> findById(String id) {
        for (CivilCar civilCar : civilCars) {
            if (civilCar.getId().equals(id)) {
                return Optional.of(civilCar);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<CivilCar> getAll() {
        return civilCars;
    }

    @Override
    public boolean save(CivilCar savedCivilCar) {
        if (savedCivilCar == null) {
            throw new IllegalArgumentException("Civil auto must not be null");
        }
        if (savedCivilCar.getPrice().equals(BigDecimal.ZERO)) {
            savedCivilCar.setPrice(BigDecimal.valueOf(-1));
        }
        return civilCars.add(savedCivilCar);
    }

    @Override
    public boolean saveAll(List<CivilCar> savedCivilCars) {
        if (savedCivilCars == null) {
            throw new IllegalArgumentException("Civil autos must not be null");
        }
        return civilCars.addAll(savedCivilCars);
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
    public boolean deleteById(String id) {
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