package org.example.reprository;

import org.example.model.CivilCar;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CivilCarRepository implements CrudRepository<CivilCar> {
    private static CivilCarRepository instance;
    public final List<CivilCar> civilCars;

    private CivilCarRepository() {
        civilCars = new LinkedList<>();
    }

    public static CivilCarRepository getInstance() {
        if (instance == null) {
            instance = new CivilCarRepository();
        }
        return instance;
    }

    @Override
    public Optional<CivilCar> findById(String id) {
        return civilCars.stream()
                .filter(civilCar -> civilCar.getId().equals(id))
                .findAny();
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
    public void update(CivilCar auto) {
        Optional<CivilCar> optionalAuto = findById(auto.getId());
        optionalAuto.ifPresentOrElse(founded -> CivilCarCopy.copy(auto, founded),
                () -> save(auto));
    }


    @Override
    public boolean deleteById(String id) {
        return civilCars.removeIf(auto -> auto.getId().equals(id));
    }

    @Override
    public boolean delete(CivilCar civilCar) {
        return civilCars.remove(civilCar);
    }

    public void resetForTest() {
        civilCars.clear();
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